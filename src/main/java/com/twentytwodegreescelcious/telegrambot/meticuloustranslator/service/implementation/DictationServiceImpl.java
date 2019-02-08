package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.MTUserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.DictationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
public class DictationServiceImpl implements DictationService {

    @Autowired
    private MTUserDao mtUserDao;

    @Override
    public void register(Integer chatId, String text) {
        MTUser mtUser = new MTUser();
        mtUser.setId(chatId);
        mtUser.setDefaultLanguage(text);
        mtUserDao.save(mtUser);
    }
}
