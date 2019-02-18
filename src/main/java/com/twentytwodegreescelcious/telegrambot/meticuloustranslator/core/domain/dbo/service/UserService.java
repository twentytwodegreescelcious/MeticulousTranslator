package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
public interface UserService {
    User createMTUser(User user);
    User getMTUser(Integer id);
    User editMTUser(User user);
    void deleteMTUser(User user);
    void deleteMTUser(Integer id);
    List<User> getAllMTUsers();
    Long countMTUsers();
    String setLanguage(Integer chatId, String text);
    String newTopic(Integer chatId, String text);
    String closeTopic(Integer chatId);
}
