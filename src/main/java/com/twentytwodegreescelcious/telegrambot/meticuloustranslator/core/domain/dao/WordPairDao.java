package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Repository
public interface WordPairDao extends CrudRepository<WordPair, Long> {
}
