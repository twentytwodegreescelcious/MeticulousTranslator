package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.MTUserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Language;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.LanguageNotFoundException;
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

    @Autowired
    private MTUserDao mtUserDao;

    @Override
    public MTUser createMTUser(MTUser mtUser) {
        return mtUserDao.save(mtUser);
    }

    @Override
    public MTUser getMTUser(Integer id) {
        Optional<MTUser> optional= mtUserDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else return null;
    }

    @Override
    public MTUser editMTUser(MTUser mtUser) {
        return mtUserDao.saveAndFlush(mtUser);
    }

    @Override
    public void deleteMTUser(MTUser mtUser) {
        mtUserDao.delete(mtUser);
    }

    @Override
    public void deleteMTUser(Integer id) {
        mtUserDao.deleteById(id);
    }

    @Override
    public List<MTUser> getAllMTUsers() {
        return mtUserDao.findAll();
    }

    @Override
    public Long countMTUsers() {
        return mtUserDao.count();
    }

    @Override
    public String setLanguage(Integer chatId, String text) {
        try {
            String language = Language.find(text.substring(12));
            MTUser user = this.getMTUser(chatId);
            if (null == user) {
                user = new MTUser();
                user.setId(chatId);
                user.setDefaultLanguage(language);
                this.createMTUser(user);
            } else {
                user.setDefaultLanguage(language);
                this.editMTUser(user);
            }

            return "Your default language is successfully set to " + language;
        } catch (LanguageNotFoundException exc) {
            return "The requested language does not seem to be supported, sorry.";
        }
    }

    @Override
    public String newTopic(Integer chatId, String text) {
        MTUser user = this.getMTUser(chatId);
        if (null == user) {
            return "You didn't specify your mother tongue. Use /setlanguage command first.";
        } else {
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
    }

    @Override
    public String closeTopic(Integer chatId) {
        MTUser user = this.getMTUser(chatId);
        if (null == user) {
            return "You didn't start any topic yet. Please specify your mother tongue before doing so. Use /setlanguage command first.";
        } else {
            if (null == user.getCurrentTopic()) {
                return "You didn't start any topic yet. Use /newtopic to start a new topic.";
            }
            String ret = user.getCurrentTopic();
            user.setCurrentTopic(null);
            return "You successfully closed " + ret + " topic.";
        }
    }
}
