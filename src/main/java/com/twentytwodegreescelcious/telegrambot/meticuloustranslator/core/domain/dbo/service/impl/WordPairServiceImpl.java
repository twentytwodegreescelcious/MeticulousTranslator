package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    public WordPair createWordPair(WordPair wordPair) {
        return wordPairDao.save(wordPair);
    }

    @Override
    public String createWordPair(Integer chatId, String wordPair) {
        MTUser user = userService.getMTUser(chatId);
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
                    return "Word pair " + wordAndTranslation[0] + " (word) || " + wordAndTranslation[1] + " (translation) " +
                            "already exists within " + user.getCurrentTopic() + " topic.";
                }
                WordPair wp = new WordPair();
                wp.setWord(wordAndTranslation[0]);
                wp.setTranslation(wordAndTranslation[1]);
                wp.setUser(user);
                wp.setTopic(user.getCurrentTopic());
                wordPairDao.save(wp);
                return "Word pair " + wordAndTranslation[0] + " (word) || " + wordAndTranslation[1] + " (translation) " +
                        "has been successfully saved within the " + wp.getTopic() + " topic.";
            }
        } else {
            return "You can't save word that are not related to any topic." +
                    "\nYou are seeing this because didn't start any topic yet. Use /newtopic to start a new topic.";
        }

    }

    @Override
    public WordPair getWordPair(Integer id) {
        if (wordPairDao.existsById(id)) {
            return wordPairDao.getOne(id);
        } else return null;
    }

    @Override
    public WordPair editWordPair(WordPair wordPair) {
        return wordPairDao.save(wordPair);
    }

    @Override
    public void deleteWordPair(WordPair wordPair) {
        wordPairDao.delete(wordPair);
    }

    @Override
    public void deleteWordPair(Integer id) {
        wordPairDao.deleteById(id);
    }

    @Override
    public List<WordPair> getAllWordPairs() {
        return wordPairDao.findAll();
    }

    @Override
    public String getWordPairsByTopic(String topic) {
        String r = "" ;
        List<WordPair> wordPairs = wordPairDao.findByTopicIgnoreCase(topic);
        if (wordPairs.isEmpty()) {
            return "Sorry, but the requested topic " + topic + " is empty or does not exist.";
        } else {
            r = "Showing word pairs for " + topic + " topic:\n";
            for(WordPair wp : wordPairs) {
                r += wp.getWord();
                r+= " || ";
                r+=wp.getTranslation();
                r+="\n\n";
            }
        }
        return r;
    }

    @Override
    public Long countWordPairs() {
        return wordPairDao.count();
    }

    @Override
    public List<String> getTopics(MTUser mtUser) {
        return null;
    }

    @Override
    public List<String> getTopics(Integer chatId) {
        return null;
    }
}
