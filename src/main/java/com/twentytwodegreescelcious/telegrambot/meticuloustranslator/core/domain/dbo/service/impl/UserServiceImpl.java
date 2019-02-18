package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.UserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Language;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.LanguageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User createMTUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getMTUser(Integer id) {
        Optional<User> optional = userDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public User editMTUser(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    public void deleteMTUser(User user) {
        userDao.delete(user);
    }

    @Override
    public void deleteMTUser(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> getAllMTUsers() {
        return userDao.findAll();
    }

    @Override
    public Long countMTUsers() {
        return userDao.count();
    }

    @Override
    public String setLanguage(Integer chatId, String text) {
        try {
            String language = Language.find(text.substring(12));
            User user = this.getMTUser(chatId);
            if (null == user) {
                user = new User();
                user.setId(chatId);
                user.setDefaultLanguage(language);
                this.createMTUser(user);
            } else {
                user.setDefaultLanguage(language);
                this.editMTUser(user);
            }

            return "Your default language is successfully set to " + language;
        } catch (LanguageNotFoundException exc) {
            logger.error("User requested language that is not supported.");
            return "The requested language does not seem to be supported, sorry.";
        }
    }

    @Override
    public String newTopic(Integer chatId, String text) {
        User user = this.getMTUser(chatId);
        if (null == user) {
            return "You didn't specify your mother tongue. Use /setlanguage command first.";
        }
        if (null != user.getCurrentTopic()) {
            return "You didn't close your previous topic which is " +
                    user.getCurrentTopic() +
                    ".\nPlease use /closetopic command to stop adding words related to this topic.";
        }
        user.setCurrentTopic(text);
        this.editMTUser(user);
        return "You successfully started a new topic." +
                "\nUse /addpair to add a pair of words to this topic." +
                "\nAll the words you add will be related to this topic." +
                "\nAs soon as you are done with adding words to current topic" +
                "use /closetopic command to stop adding words related to this topic.";

    }

    @Override
    public String closeTopic(Integer chatId) {
        User user = this.getMTUser(chatId);
        if (null == user) {
            return "You didn't start any topic yet. Please specify your mother tongue before doing so. Use /setlanguage command first.";
        }
        if (null == user.getCurrentTopic()) {
            return "You didn't start any topic yet. Use /newtopic to start a new topic.";
        }
        String ret = user.getCurrentTopic();
        user.setCurrentTopic(null);
        return "You successfully closed " + ret + " topic.";

    }
}
