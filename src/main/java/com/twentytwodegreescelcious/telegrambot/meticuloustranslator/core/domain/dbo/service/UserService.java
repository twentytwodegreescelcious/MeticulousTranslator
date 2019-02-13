package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
public interface UserService {
    MTUser createMTUser(MTUser mtUser);
    MTUser getMTUser(Integer id);
    MTUser editMTUser(MTUser mtUser);
    void deleteMTUser(MTUser mtUser);
    void deleteMTUser(Integer id);
    List<MTUser> getAllMTUsers();
    Long countMTUsers();
    String setLanguage(Integer chatId, String text);
    String newTopic(Integer chatId, String text);
    String closeTopic(Integer chatId);
}
