package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
public interface MTUserService {
    MTUser createMTUser(MTUser mtUser);
    MTUser getMTUser(Integer id);
    MTUser editMTUser(MTUser mtUser);
    void deleteMTUser(MTUser mtUser);
    void deleteMTUser(Integer id);
    List<MTUser> getAllMTUsers();
    Long countMTUsers();
}
