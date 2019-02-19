package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
@Service
@Transactional
public class WordPairServiceImpl implements WordPairService {

    @Autowired
    private MessageSource messageSource;

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
        User user = userService.getMTUser(chatId);
        if (null != user && null != user.getCurrentTopic()) {
            String[] wordAndTranslation = wordPair.split(" - ");
            if (wordAndTranslation.length > 2) {
                return messageSource.getMessage("wordserviceimpl.createwordpair.specialchar",
                        null, new Locale(user.getDefaultLanguage()));
            } else if (wordAndTranslation.length < 2) {
                return messageSource.getMessage("wordserviceimpl.createwordpair.incorrectformat",
                        null, new Locale(user.getDefaultLanguage()));
            } else {
                if (null != wordPairDao.findByWordAndTranslationAndTopicIgnoreCase(
                        wordAndTranslation[0], wordAndTranslation[1], user.getCurrentTopic())) {
                    return messageSource.getMessage("wordserviceimpl.createwordpair.alreadyexists",
                            new Object[]{wordAndTranslation[0], wordAndTranslation[1], user.getCurrentTopic()},
                            new Locale(user.getDefaultLanguage()));
                }
                WordPair wp = new WordPair();
                wp.setWord(wordAndTranslation[0]);
                wp.setTranslation(wordAndTranslation[1]);
                wp.setUser(user);
                wp.setTopic(user.getCurrentTopic());
                wordPairDao.save(wp);
                return messageSource.getMessage("wordserviceimpl.createwordpair.success",
                        new Object[]{wordAndTranslation[0], wordAndTranslation[1], wp.getTopic()},
                        new Locale(user.getDefaultLanguage()));
            }
        } else {
            return messageSource.getMessage("wordserviceimpl.createwordpair.notopic", null,
                    (user == null) ? Locale.ENGLISH : new Locale(user.getDefaultLanguage()));
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
    public String getWordPairs(String topic) {
        String r = "";
        StringBuilder sb;
        List<WordPair> wordPairs = wordPairDao.findByTopicIgnoreCase(topic);
        if (wordPairs.isEmpty()) {
            return messageSource.getMessage("wordserviceimpl.crgetwordpairs.doesnotexist", new Object[]{topic},
                    Locale.ENGLISH);
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

    @Override
    public Long countWordPairs() {
        return wordPairDao.count();
    }

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
