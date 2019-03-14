package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairQuizInfoDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairQuizInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by twentytwodegreescelcious on 3/14/2019.
 */
@Service
public class WordPairQuizInfoServiceImpl implements WordPairQuizInfoService {

    @Autowired
    private WordPairQuizInfoDao wordPairQuizInfoDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WordPairQuizInfo createWordPairQuizInfo(WordPairQuizInfo wordPairQuizInfo) {
        return wordPairQuizInfoDao.save(wordPairQuizInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public WordPairQuizInfo getWordPairQuizInfo(Integer id) {
        if (wordPairQuizInfoDao.existsById(id)) {
            return wordPairQuizInfoDao.getOne(id);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WordPairQuizInfo updateWordPairQuizInfo(WordPairQuizInfo wordPairQuizInfo) {
        return wordPairQuizInfoDao.save(wordPairQuizInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteWordPairQuizInfo(Integer id) {
        wordPairQuizInfoDao.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteWordPairQuizInfo(WordPairQuizInfo wordPairQuizInfo) {
        wordPairQuizInfoDao.delete(wordPairQuizInfo);
    }
}
