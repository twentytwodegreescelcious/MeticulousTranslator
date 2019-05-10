package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
public interface WordPairService {

    /**
     * Provides with "CREATE" operation.
     * The wordPair argument must contain certain fields defined before passing to this method.
     *
     * @param wordPair an object representing a database row for "WORD_PAIR" table that is attempting to be inserted.
     * @return an object representing a database row for "WORD_PAIR" table that has just been inserted.
     */
    WordPair createWordPair(WordPair wordPair);

    /**
     * Provides with "CREATE" operation.
     * The wordPair argument must contain certain fields defined before passing to this method.
     *
     * @param chatId   an integer value that corresponds to the Telegram chat id.
     * @param wordPair an java.lang.String representing a database row for "WORD_PAIR" table
     *                 that is attempting to be inserted.
     * @return an object representing a database row for "WORD_PAIR" table that has just been inserted.
     */
    String createWordPair(Integer chatId, String wordPair);

    /**
     * Provides with "READ" operation.
     * The integer argument must specify id of the word pair that is desired to be read.
     *
     * @param id integer value that corresponds to the word pair entity id.
     * @return an object representing a database row for "WORD_PAIR" table that has just been read.
     */

    WordPair getWordPair(Integer id);

    /**
     * Provides with "UPDATE" operation.
     * The wordPair object must contain the id value of the object that is being updated and the correct values
     * of all the fields.
     *
     * @param wordPair represents a row in "WORD_PAIR" table that is attempting to be modified.
     * @return an object representing a row in "WORD_PAIR" table that has been modified.
     */
    WordPair editWordPair(WordPair wordPair);

    /**
     * Provides with "DELETE" operation.
     * The wordPair argument must contain a defined id field that corresponds to the id of the word pair
     * that is desired to be deleted.
     * @param wordPair an object representing a database row for "WORD_PAIR" table that is attempting to be deleted.
     */
    void deleteWordPair(WordPair wordPair);

    /**
     * Provides with "DELETE" operation.
     * The wordPair argument must contain a defined id field that corresponds to the id of the word pair
     * that is desired to be deleted.
     * @param id an integer value representing a database row for "WORD_PAIR" table that is attempting to be deleted.
     */
    void deleteWordPair(Integer id);

    /**
     * Provides with "READ" operation.
     *
     * @return a java.util.List of all the entities that are present in the database.
     */
    List<WordPair> getAllWordPairs();

    /**
     * Provides with "READ" operation.
     * Returns a java.lang.String representation of the word pairs that pertain the specified topic.
     *
     * @param topic a java.lang.String value that contains the name of a topic by which we attempt
     *             to read the word pairs.
     * @return A java.lang.String value that is designed to notify user about success/failure of current operation
     *         execution <i>or</i> a character representation of the word pairs that pertain the specified topic.
     */
    String getWordPairs(String topic);

    /**
     * Counts the quantity of the rows that are present in the database.
     *
     * @return java.lang.Long value of the word pair entities in the database.
     */
    Long countWordPairs();

    /**
     * Returns a java.util.List of java.lang.String that are the topics that a specified user created.
     * @param user a User object which is used as a parameter to define the needed topics.
     * @return A java.util.List of topics (represented as java.lang.String) which were created by the specified user.
     */
    List<String> getTopics(User user);

    /**
     * Returns a java.util.List of java.lang.String that are the topics that a specified user created.
     * @param chatId an integer value which is used as a parameter to define the needed topics.
     * @return A java.util.List of topics (represented as java.lang.String) which were created by the specified user.
     */
    List<String> getTopics(Integer chatId);

    /**
     * Gets all the word pairs that belong to a user's topic.
     * @param user Object representation of a USERS table in database.
     * @param topic Topic name.
     * @return A List of WordPair object that corresponds to user and topic.
     */
    List<WordPair> getWordPairsForUserTopic(User user, String topic);

    /**
     * Gets all the word pairs that belong to a user's topic but haven't been answered in the latest quiz.
     * @param user  Object representation of a USERS table in database.
     * @param topic Topic name.
     * @return A List of WordPair object that corresponds to user and topic and weren't answered in the latest quiz.
     */
    List<WordPair> getAnsweredWordPairsForUserTopic(User user, String topic);
}
