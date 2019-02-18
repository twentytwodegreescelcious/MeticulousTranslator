package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
@Service
@Transactional
public class WordPairServiceImpl implements WordPairService {

    @Autowired
    private WordPairDao wordPairDao;

    @Autowired
    private UserService userService;

    /**
     * Provides with "CREATE" operation.
     * The wordPair argument must contain certain fields defined before passing to this method.
     *
     * @param wordPair an object representing a database row for "WORD_PAIR" table that is attempting to be inserted.
     * @return an object representing a database row for "WORD_PAIR" table that has just been inserted.
     */
    @Override
    public WordPair createWordPair(WordPair wordPair) {
        return wordPairDao.save(wordPair);
    }

    /**
     * Provides with "CREATE" operation.
     * The wordPair argument must contain certain fields defined before passing to this method.
     *
     * @param chatId   an integer value that corresponds to the Telegram chat id.
     * @param wordPair an java.lang.String representing a database row for "WORD_PAIR" table
     *                 that is attempting to be inserted.
     * @return an object representing a database row for "WORD_PAIR" table that has just been inserted.
     */
    @Override
    public String createWordPair(Integer chatId, String wordPair) {
        User user = userService.getMTUser(chatId);
        if (null != user && null != user.getCurrentTopic()) {
            String[] wordAndTranslation = wordPair.split(" - ");
            if (wordAndTranslation.length > 2) {
                return "For a reason we disallow to use '-' sign with spaces for any reason " +
                        "except for separation of the word and translation.";
            } else if (wordAndTranslation.length < 2) {
                return "I didn't save that. The correct format is: \"word - translation\"." +
                        "\nExcuse me for these formalities, I'm just a bot after all." +
                        "\nOne day I'll become intelligent..." +
                        "\nTomorrow might be the day.";
            } else {
                if (null != wordPairDao.findByWordAndTranslationAndTopicIgnoreCase(
                        wordAndTranslation[0], wordAndTranslation[1], user.getCurrentTopic())) {
                    return "Word pair " + wordAndTranslation[0] +
                            " (word) || " + wordAndTranslation[1] + " (translation) " +
                            "already exists within " + user.getCurrentTopic() + " topic.";
                }
                WordPair wp = new WordPair();
                wp.setWord(wordAndTranslation[0]);
                wp.setTranslation(wordAndTranslation[1]);
                wp.setUser(user);
                wp.setTopic(user.getCurrentTopic());
                wordPairDao.save(wp);
                return "Word pair " + wordAndTranslation[0] + " (word) || " + wordAndTranslation[1] +
                        " (translation) " + "has been successfully saved within the " + wp.getTopic() + " topic.";
            }
        } else {
            return "You can't save word that are not related to any topic." +
                    "\nYou are seeing this because didn't start any topic yet. Use /newtopic to start a new topic.";
        }

    }

    /**
     * Provides with "READ" operation.
     * The integer argument must specify id of the word pair that is desired to be read.
     *
     * @param id integer value that corresponds to the word pair entity id.
     * @return an object representing a database row for "WORD_PAIR" table that has just been read.
     */
    @Override
    public WordPair getWordPair(Integer id) {
        if (wordPairDao.existsById(id)) {
            return wordPairDao.getOne(id);
        } else return null;
    }

    /**
     * Provides with "UPDATE" operation.
     * The wordPair object must contain the id value of the object that is being updated and the correct values
     * of all the fields.
     *
     * @param wordPair represents a row in "WORD_PAIR" table that is attempting to be modified.
     * @return an object representing a row in "WORD_PAIR" table that has been modified.
     */
    @Override
    public WordPair editWordPair(WordPair wordPair) {
        return wordPairDao.save(wordPair);
    }

    /**
     * Provides with "DELETE" operation.
     * The wordPair argument must contain a defined id field that corresponds to the id of the word pair
     * that is desired to be deleted.
     * @param wordPair an object representing a database row for "WORD_PAIR" table that is attempting to be deleted.
     */
    @Override
    public void deleteWordPair(WordPair wordPair) {
        wordPairDao.delete(wordPair);
    }

    /**
     * Provides with "DELETE" operation.
     * The wordPair argument must contain a defined id field that corresponds to the id of the word pair
     * that is desired to be deleted.
     * @param id an integer value representing a database row for "WORD_PAIR" table that is attempting to be deleted.
     */
    @Override
    public void deleteWordPair(Integer id) {
        wordPairDao.deleteById(id);
    }

    /**
     * Provides with "READ" operation.
     *
     * @return a java.util.List of all the entities that are present in the database.
     */
    @Override
    public List<WordPair> getAllWordPairs() {
        return wordPairDao.findAll();
    }

    /**
     * Provides with "READ" operation.
     * Returns a java.lang.String representation of the word pairs that pertain the specified topic.
     *
     * @param topic a java.lang.String value that contains the name of a topic by which we attempt
     *             to read the word pairs.
     * @return A java.lang.String value that is designed to notify user about success/failure of current operation
     *         execution <i>or</i> a character representation of the word pairs that pertain the specified topic.
     */
    @Override
    public String findWordPairsByTopic(String topic) {
        String r = "";
        StringBuilder sb;
        List<WordPair> wordPairs = wordPairDao.findByTopicIgnoreCase(topic);
        if (wordPairs.isEmpty()) {
            return "Sorry, but the requested topic " + topic + " is empty or does not exist.";
        } else {
            r = "Showing word pairs for " + topic + " topic:\n";
             sb = new StringBuilder(r);
            for (WordPair wp : wordPairs) {
                sb.append(wp.getWord());
                sb.append(" || ");
                sb.append(wp.getTranslation());
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }

    /**
     * Counts the quantity of the rows that are present in the database.
     *
     * @return java.lang.Long value of the word pair entities in the database.
     */
    @Override
    public Long countWordPairs() {
        return wordPairDao.count();
    }

    /**
     * Returns a java.util.List of java.lang.String that are the topics that a specified user created.
     * @param user a User object which is used as a parameter to define the needed topics.
     * @return A java.util.List of topics (represented as java.lang.String) which were created by the specified user.
     */
    @Override
    public List<String> getTopics(User user) {
        List<WordPair> wordPairs = wordPairDao.findByUser(user);
        Set<String> topics = new HashSet<>();
        for (WordPair wp : wordPairs) {
            topics.add(wp.getTopic().trim());
        }
        List<String> topicsList = new ArrayList<>();
        topicsList.addAll(topics);
        return topicsList;
    }

    /**
     * Returns a java.util.List of java.lang.String that are the topics that a specified user created.
     * @param chatId an integer value which is used as a parameter to define the needed topics.
     * @return A java.util.List of topics (represented as java.lang.String) which were created by the specified user.
     */
    @Override
    public List<String> getTopics(Integer chatId) {
        List<WordPair> wordPairs = wordPairDao.findByUser(userService.getMTUser(chatId));
        Set<String> topics = new HashSet<>();
        for (WordPair wp : wordPairs) {
            topics.add(wp.getTopic().trim());
        }
        List<String> topicsList = new ArrayList<>();
        topicsList.addAll(topics);
        return topicsList;
    }
}
