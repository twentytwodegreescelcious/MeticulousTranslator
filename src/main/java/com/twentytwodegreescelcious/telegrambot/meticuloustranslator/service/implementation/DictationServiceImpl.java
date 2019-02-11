package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.MTUserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.DictationService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.LanguageNotFoundException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Languages;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
public class DictationServiceImpl implements DictationService {

    @Autowired
    private MTUserDao mtUserDao;

    @Override
    public String setLanguage(Integer chatId, String text) {
        try {
            return "Your default language is successfully set to " + Languages.find(text.substring(12));
        } catch(LanguageNotFoundException exc) {

        }
        return "The requested language does not seem to be supported, sorry.";
    }
}
