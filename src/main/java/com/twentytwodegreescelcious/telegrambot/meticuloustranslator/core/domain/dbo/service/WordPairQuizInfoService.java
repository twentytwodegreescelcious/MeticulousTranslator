package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;

/**
 * Created by twentytwodegreescelcious on 3/12/2019.
 */
public interface WordPairQuizInfoService {

    WordPairQuizInfo create();
    WordPairQuizInfo get();
    WordPairQuizInfo update();
    void delete();
}
