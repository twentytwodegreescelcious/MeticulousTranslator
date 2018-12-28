package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Message;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Updates;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.Translation;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.TranslatorService;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

import java.util.List;

public class UpdateHandlerImpl implements UpdateHandler{

    private TranslatorService translatorService = new TranslatorService();

    @Override
    public void onUpdate(Updates updates) {
        Message message = updates.getResults().get(updates.getResults().size()-1).getMessage();
        int chatId = message.getChat().getId();
        String translated = message.getText();

        List<Translation> translations = translatorService.getTranslations(translated);
        if (!translations.isEmpty()) {
            translations.forEach(t -> new BotMessage(chatId, translated).send());
        } else {
            new BotMessage(chatId, "No translation found for " + translated).send();
        }
    }
}
