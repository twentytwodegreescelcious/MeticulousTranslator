package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by twentytwodegreescelcious on 2/27/2019.
 */
@Repository
public interface WordPairQuizInfoDao extends JpaRepository<WordPairQuizInfo, Integer> {
}
