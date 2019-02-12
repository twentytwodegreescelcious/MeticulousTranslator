package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.MTUserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.DictationService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Language;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.LanguageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Service
@Transactional
public class DictationServiceImpl implements DictationService {

    @Autowired
    private MTUserService mtUserService;

    @Override
    public String setLanguage(Integer chatId, String text) {
        try {
            String language = Language.find(text.substring(12));
            MTUser user = mtUserService.getMTUser(chatId);
            if (null == user) {
                user = new MTUser();
                user.setId(chatId);
                user.setDefaultLanguage(language);
                mtUserService.createMTUser(user);
            } else {
                user.setDefaultLanguage(language);
                mtUserService.editMTUser(user);
            }

            return "Your default language is successfully set to " + language;
        } catch (LanguageNotFoundException exc) {

        }
        return "The requested language does not seem to be supported, sorry.";
    }
}
