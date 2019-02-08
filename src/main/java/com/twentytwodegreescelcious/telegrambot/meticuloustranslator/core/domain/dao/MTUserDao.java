package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Repository
public interface MTUserDao extends CrudRepository<MTUser, Long> {
}
