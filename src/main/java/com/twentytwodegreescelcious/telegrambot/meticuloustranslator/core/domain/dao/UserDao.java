package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
}
