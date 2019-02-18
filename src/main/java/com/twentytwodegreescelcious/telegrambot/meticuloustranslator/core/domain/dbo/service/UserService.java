package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
public interface UserService {

    /**
     * Provides with "CREATE" operation.
     * The user argument must contain certain fields defined before passing to this method.
     *
     * @param user an object representing a database row for "MT_USER" table that is attempting to be inserted.
     * @return an object representing a database row for "MT_USER" table that has just been inserted.
     */
    User createMTUser(User user);

    /**
     * Provides with "READ" operation.
     * The integer argument must specify telegram chat id of the user that is desired to be read.
     *
     * @param id integer value that corresponds to the Telegram chat id.
     * @return an object representing a database row for "MT_USER" table that has just been read.
     */
    User getMTUser(Integer id);

    /**
     * Provides with "UPDATE" operation.
     * The user argument must contain a defined id field that corresponds to the id of the user
     * that is desired to be updated.
     *
     * @param user an object representing an updated version of the object that is attempting to be updated.
     * @return an object representing a database row for "MT_USER" table that has just be updated.
     */
    User editMTUser(User user);

    /**
     * Provides with "DELETE" operation.
     * The user argument must contain a defined id field that corresponds to the id of the user
     * that is desired to be deleted.
     * @param user an object representing a database row for "MT_USER" table that is attempting to be deleted.
     */
    void deleteMTUser(User user);

    /**
     * Provides with "DELETE" operation.
     * The integer argument must specify value that corresponds to the id of the user
     * that is desired to be deleted.
     * @param id An integer value that specifies a database row for "MT_USER" table that is attempting to be deleted.
     */
    void deleteMTUser(Integer id);

    /**
     * An operation that returns the java.util.List of all users that are present in the database.
     *
     * @return java.util.List of users.
     */
    List<User> getAllMTUsers();

    /**
     * An operation that returns the quantity of users that are present in the database.
     *
     * @return java.lang.Long value that corresponds to the quantity of entities that are present in the database
     */
    Long countMTUsers();

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
    String setLanguage(Integer chatId, String text);

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
    String newTopic(Integer chatId, String text);

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
    String closeTopic(Integer chatId);
}
