package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Message;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Updates;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.Translation;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.TranslatorService;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

import java.util.List;

public class UpdateHandlerImpl implements UpdateHandler{

    private final static String ENDPOINT = "https://api.telegram.org/";
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

    @Override
    public HttpResponse<JsonNode> sendMessage(String token, Integer chatId, String text) throws UnirestException {
        return Unirest.post(ENDPOINT + token + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson();
    }

    @Override
    public HttpResponse<JsonNode> getUpdates(String token, Long offset) throws UnirestException {
        return Unirest.post(ENDPOINT + token + "/getUpdates")
                .field("offset", offset)
                .asJson();
    }

}
