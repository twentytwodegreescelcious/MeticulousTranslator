package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Repository
public interface WordPairDao extends JpaRepository<WordPair, Integer> {
    List<WordPair> findByTopicIgnoreCase(String topic);
    WordPair findByWordAndTranslationAndTopicIgnoreCase(String word, String translation, String topic);
}
