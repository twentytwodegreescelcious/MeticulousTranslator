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

    /**
     * Provides with "CREATE" operation.
     * The user argument must contain certain fields defined before passing to this method.
     *
     * @param user an object representing a database row for "MT_USER" table that is attempting to be inserted.
     * @return an object representing a database row for "MT_USER" table that has just been inserted.
     */
    @Override
    public User createMTUser(User user) {
        return userDao.save(user);
    }

    /**
     * Provides with "READ" operation.
     * The integer argument must specify telegram chat id of the user that is desired to be read.
     *
     * @param id integer value that corresponds to the Telegram chat id.
     * @return an object representing a database row for "MT_USER" table that has just been read.
     */
    @Override
    public User getMTUser(Integer id) {
        Optional<User> optional = userDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * Provides with "UPDATE" operation.
     * The user argument must contain a defined id field that corresponds to the id of the user
     * that is desired to be updated.
     *
     * @param user an object representing an updated version of the object that is attempting to be updated.
     * @return an object representing a database row for "MT_USER" table that has just be updated.
     */
    @Override
    public User editMTUser(User user) {
        return userDao.saveAndFlush(user);
    }

    /**
     * Provides with "DELETE" operation.
     * The user argument must contain a defined id field that corresponds to the id of the user
     * that is desired to be deleted.
     * @param user an object representing a database row for "MT_USER" table that is attempting to be deleted.
     */
    @Override
    public void deleteMTUser(User user) {
        userDao.delete(user);
    }

    /**
     * Provides with "DELETE" operation.
     * The integer argument must specify value that corresponds to the id of the user
     * that is desired to be deleted.
     * @param id An integer value that specifies a database row for "MT_USER" table that is attempting to be deleted.
     */
    @Override
    public void deleteMTUser(Integer id) {
        userDao.deleteById(id);
    }

    /**
     * An operation that returns the java.util.List of all users that are present in the database.
     *
     * @return java.util.List of users.
     */
    @Override
    public List<User> getAllMTUsers() {
        return userDao.findAll();
    }


    /**
     * An operation that returns the quantity of users that are present in the database.
     *
     * @return java.lang.Long value that corresponds to the quantity of entities that are present in the database
     */
    @Override
    public Long countMTUsers() {
        return userDao.count();
    }

    /**
     * Provides with an "UPDATE" operation on a language column in a row of "MT_USER" table with an appropriate chat id.
     *
     * @param chatId An integer value that specifies a database row for "MT_USER" table
     *               that is attempting to be modified.
     * @param text A java.lang.String value that contains the request query of language changing and the full character
     *             value of the language user wants to be set as his/her default language.
     * @return A java.lang.String value that is designed to notify user about success/failure of current operation
     *         execution.
     */
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

    /**
     * Provides with an "UPDATE" operation on a topic column in a row of "MT_USER" table with an appropriate chat id.
     * The rule is: user can change the <b>null value only</b>. This way, if the value of MT_USER.topic != null
     * the user is required to start a procedure that closes the topic that has been started previously.
     *
     * @param chatId An integer value that specifies a database row for "MT_USER" table
     *               that is attempting to be modified.
     * @param text A java.lang.String value that contains a name of the topic the user wishes to start.
     * @return A java.lang.String value that is designed to notify user about success/failure of current operation
     *         execution.
     */
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

    /**
     * Provides with an "UPDATE" operation on a topic column in a row of "MT_USER" table with an appropriate chat id.
     * The rule is: user can change the <b>not null value only</b>. This way, if the value of MT_USER.topic == NULL
     * the user is required to start a procedure of creating topic.
     *
     * @param chatId An integer value that specifies a database row for "MT_USER" table
     *               that is attempting to be modified.
     * @return A java.lang.String value that is designed to notify user about success/failure of current operation
     *         execution.
     */
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
