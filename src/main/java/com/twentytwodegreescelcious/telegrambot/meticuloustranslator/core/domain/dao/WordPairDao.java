package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Repository
public interface WordPairDao extends JpaRepository<WordPair, Integer> {

    /**
     * Provides with a connection to the database.
     * Implements query execution that leads to finding all the word pairs that have the topic column matching
     * the given parameter.
     * Returns a collection of word pair objects.
     *
     * @param topic a character value of the topic user requests.
     * @return a java.util.List of object representing row(s) in the "WORD_PAIR" table
     */
    List<WordPair> findByTopicIgnoreCase(String topic);

    /**
     * Provides with a connection to the database.
     * Main purpose is to ensure that there are no "same" word pairs contained in the "WORD_PAIR" table.
     * Returns a WordPair object in case there is a word pair with the given (word, translation and topic)
     * fields present in the database or <b>null</b> in case there is no such row.
     *
     * @param word a java.lang.String that represents the word to be translated.
     * @param translation a java.lang.String that represents the translation for the given word.
     * @param topic a java.lang.String that represents the topic to which the "word" and "translation" correspond.
     * @return <b>null</b> in case there is no such word pair, WordPair object, otherwise.
     */
    WordPair findByWordAndTranslationAndTopicIgnoreCase(String word, String translation, String topic);

    /**
     * Provides with a connection to the database.
     * Returns all the word pairs that were added by the given user.
     *
     * @param user an object representation of the foreign key. Must contain a defined chatId field.
     * @return java.util.List of word pair objects that correspond to the rows in the "WORD_PAIR" table.
     */
    List<WordPair> findByUser(User user);

    /**
     * Provides with a connection to the database.
     * Implements a query execution that get all the wordpairs that belong to a user and correspond to a given topic.
     *
     * @param user an object representation of the foreign key. Must contain a defined chatId field.
     * @param topic a string which specifies a topic to search for.
     * @return
     */
    List<WordPair> findByUserAndTopicLikeIgnoreCase(User user, String topic);
}
