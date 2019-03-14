package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;

/**
 * Created by twentytwodegreescelcious on 3/12/2019.
 */
public interface WordPairQuizInfoService {

    /**
     * Provides with a <i>CREATE</i> operation.
     *
     * Argument object must contain defined fields.
     *
     * @param wordPairQuizInfo An object representation of the database table row.
     * @return An object representation of the object that has just been added to the database.
     */
    WordPairQuizInfo createWordPairQuizInfo(WordPairQuizInfo wordPairQuizInfo);

    /**
     * Provides with a <i>READ</i> operation.
     *
     * @param id An integer value that corresponds to the id field of the WORDPAIR_QUIZ_INFO table row tgat is
     *           to be read.
     * @return An object representation of the database table row.
     */
    WordPairQuizInfo getWordPairQuizInfo(Integer id);

    /**
     * Provides with an <i>UPDATE</i> operation.
     *
     * @param wordPairQuizInfo an object representation of the database table row that is to be modified.
     * @return an object representation of the database row that has just been modified.
     */
    WordPairQuizInfo updateWordPairQuizInfo(WordPairQuizInfo wordPairQuizInfo);

    /**
     * Provides with a <i>DELETE</i> operation.
     *
     * @param id an integer value that corresponds to the id field of the WORDPAIR_QUIZ_INFO table row that is
     *           to be deleted.
     */
    void deleteWordPairQuizInfo(Integer id);

    /**
     * Provides with a <i>DELETE</i> operation.
     *
     * @param wordPairQuizInfo an object representation of the WORDPAIR_QUIZ_INFO table row that is to be deleted.
     */
    void deleteWordPairQuizInfo(WordPairQuizInfo wordPairQuizInfo);
}
