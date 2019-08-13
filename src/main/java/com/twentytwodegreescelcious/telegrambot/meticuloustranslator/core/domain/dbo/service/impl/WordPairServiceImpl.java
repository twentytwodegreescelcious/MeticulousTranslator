package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairQuizInfoService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
@Service
public class WordPairServiceImpl implements WordPairService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WordPairDao wordPairDao;

    @Autowired
    private UserService userService;

    @Autowired
    private WordPairQuizInfoService wordPairQuizInfoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WordPair createWordPair(WordPair wordPair) {
        return wordPairDao.save(wordPair);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String createWordPair(Integer chatId, String wordPair) {
        User user = userService.getUser(chatId);
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
                WordPair wp = new WordPair(user, wordAndTranslation[0], wordAndTranslation[1], user.getCurrentTopic(),
                        wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public WordPair getWordPair(Integer id) {
        if (wordPairDao.existsById(id)) {
            return wordPairDao.getOne(id);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WordPair editWordPair(WordPair wordPair) {
        return wordPairDao.save(wordPair);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteWordPair(WordPair wordPair) {
        wordPairDao.delete(wordPair);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteWordPair(Integer id) {
        wordPairDao.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<WordPair> getAllWordPairs() {
        return wordPairDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public String getWordPairs(String topic) {
        String r;
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
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Long countWordPairs() {
        return wordPairDao.count();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<String> getTopics(Integer chatId) {
        List<WordPair> wordPairs = wordPairDao.findByUser(userService.getUser(chatId));
        Set<String> topics = new HashSet<>();
        for (WordPair wp : wordPairs) {
            topics.add(wp.getTopic().trim());
        }
        List<String> topicsList = new ArrayList<>();
        topicsList.addAll(topics);
        return topicsList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<WordPair> getWordPairsForUserTopic(User user, String topic) {
        if (null != user) {
            if (null != topic && !topic.isEmpty()) {
                return wordPairDao.findByUserAndTopicLikeIgnoreCase(user, "%" + topic.trim() + "%");
            }
            String currentTopic = user.getCurrentTopic();
            if (null != currentTopic && !currentTopic.isEmpty()) {
                return wordPairDao.findByUserAndTopicLikeIgnoreCase(user, "%" + currentTopic.trim() + "%");
            }
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<WordPair> getAnsweredWordPairsForUserTopic(User user, String topic) {
        List<WordPair> wordPairs = wordPairDao.findByUserAndTopicLikeIgnoreCase(user, topic);
        for (WordPair wp : wordPairs) {
            WordPairQuizInfo wpqi = wp.getWordPairQuizInfo();
            if (!wpqi.getInQuiz()) {
                wordPairs.add(wp);
            } else {
                wpqi.setCurrent(false);
                wpqi.setInQuiz(false);
                wordPairQuizInfoService.updateWordPairQuizInfo(wpqi);
            }
        }
        return wordPairs;
    }
}
