package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
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

    @Override
    public WordPair createWordPair(WordPair wordPair) {
        return wordPairDao.save(wordPair);
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
    public List<WordPair> getWordPairsByTopic(String topic) {
        return wordPairDao.findByTopicIgnoreCase(topic);
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
