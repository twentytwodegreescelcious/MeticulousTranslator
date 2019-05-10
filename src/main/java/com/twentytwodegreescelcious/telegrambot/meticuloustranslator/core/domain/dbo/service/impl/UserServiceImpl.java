package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.UserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UserQuizService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Language;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.LanguageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WordPairDao wordPairDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserQuizService userQuizService;


    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User createUser(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User getUser(Integer id) {
        Optional<User> optional = userDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User editUser(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Long countUsers() {
        return userDao.count();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String setLanguage(Integer chatId, String text) {
        User user;
        try {
            String language = Language.find(text);
            user = this.getUser(chatId);
            if (null == user) {
                user = new User();
                user.setId(chatId);
                user.setDefaultLanguage(language);
                this.createUser(user);
            } else {
                user.setDefaultLanguage(language);
                this.editUser(user);
            }
            return messageSource.getMessage("userserviceimpl.setlanguage.success", null,
                    new Locale(user.getDefaultLanguage()));
        } catch (LanguageNotFoundException exc) {
            logger.error("User requested language that is not supported.");
            return messageSource.getMessage("userserviceimpl.setlanguage.notsupported", null, Locale.ENGLISH);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String newTopic(Integer chatId, String text) {
        User user = this.getUser(chatId);
        if (null == user) {
            return messageSource.getMessage("userserviceimpl.newtopic.nolangset", null,
                    new Locale("en"));
        }
        if (null != user.getCurrentTopic()) {
            return messageSource.getMessage("userserviceimpl.newtopic.notclosed",
                    new Object[]{user.getCurrentTopic()}, new Locale(user.getDefaultLanguage()));
        }
        user.setCurrentTopic(text);
        this.editUser(user);
        return messageSource.getMessage("userserviceimpl.newtopic.success", null,
                new Locale(user.getDefaultLanguage()));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String closeTopic(Integer chatId) {
        User user = this.getUser(chatId);
        if (null == user) {
            return messageSource.getMessage("userserviceimpl.closetopic.nolangset", null,
                    new Locale("en"));
        }
        if (null == user.getCurrentTopic()) {
            return messageSource.getMessage("userserviceimpl.closetopic.notstarted", null,
                    new Locale(user.getDefaultLanguage()));
        }
        String returnMessage = user.getCurrentTopic();
        user.setCurrentTopic(null);
        return messageSource.getMessage("userserviceimpl.closetopic.success", new Object[]{returnMessage},
                new Locale(user.getDefaultLanguage()));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String startQuiz(User user, String topic) {
        return userQuizService.startQuiz(user.getId(), topic);
    }
}
