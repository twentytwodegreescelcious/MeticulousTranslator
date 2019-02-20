package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.UserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Language;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.LanguageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private MessageSource messageSource;

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
        User user;
        try {
            String language = Language.find(text);
            user = this.getMTUser(chatId);
            if (null == user) {
                user = new User();
                user.setId(chatId);
                user.setDefaultLanguage(language);
                this.createMTUser(user);
            } else {
                user.setDefaultLanguage(language);
                this.editMTUser(user);
            }
            return messageSource.getMessage("userserviceimpl.setlanguage.success", null,
                    new Locale(user.getDefaultLanguage()));
        } catch (LanguageNotFoundException exc) {
            logger.error("User requested language that is not supported.");
            return messageSource.getMessage("userserviceimpl.setlanguage.notsupported", null, Locale.ENGLISH );
        }
    }

    @Override
    public String newTopic(Integer chatId, String text) {
        User user = this.getMTUser(chatId);
        if (null == user) {
            return messageSource.getMessage("userserviceimpl.newtopic.nolangset", null, new Locale("en"));
        }
        if (null != user.getCurrentTopic()) {
            return messageSource.getMessage("userserviceimpl.newtopic.notclosed", new Object[]{user.getCurrentTopic()},  new Locale(user.getDefaultLanguage()));
        }
        user.setCurrentTopic(text);
        this.editMTUser(user);
        return messageSource.getMessage("userserviceimpl.newtopic.success", null, new Locale(user.getDefaultLanguage()));

    }

    @Override
    public String closeTopic(Integer chatId) {
        User user = this.getMTUser(chatId);
        if (null == user) {
            return messageSource.getMessage("userserviceimpl.closetopic.nolangset", null, new Locale("en"));
        }
        if (null == user.getCurrentTopic()) {
            return messageSource.getMessage("userserviceimpl.closetopic.notstarted", null, new Locale(user.getDefaultLanguage()));
        }
        String ret = user.getCurrentTopic();
        user.setCurrentTopic(null);
        return messageSource.getMessage("userserviceimpl.closetopic.success", new Object[]{ret}, new Locale(user.getDefaultLanguage()));

    }
}
