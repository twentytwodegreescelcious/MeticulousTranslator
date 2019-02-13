package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
public interface WordPairService {
    WordPair createWordPair(WordPair wordPair);
    String createWordPair(Integer chatId, String wordPair);
    WordPair getWordPair(Integer id);
    WordPair editWordPair(WordPair wordPair);
    void deleteWordPair(WordPair wordPair);
    void deleteWordPair(Integer id);
    List<WordPair> getAllWordPairs();
    String getWordPairsByTopic(String topic);
    Long countWordPairs();
    List<String> getTopics(MTUser mtUser);
    List<String> getTopics(Integer chatId);
}
