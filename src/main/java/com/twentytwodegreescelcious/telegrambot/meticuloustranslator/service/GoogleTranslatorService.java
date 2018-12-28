package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;

import java.util.Collections;
import java.util.List;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class GoogleTranslatorService {

    public static final String TRANSLATOR_RESOURCE = "https://translation.googleapis.com";

    public List<Translation> getTranslationsFor(String request) {
        String url = TRANSLATOR_RESOURCE + "language/translate/v2";
        TranslatorResponse res = HttpClient.GET(url, TranslatorResponse.class);
        if (res.getTranslations() != null) {
            return res.getTranslations();
        }
        return Collections.emptyList();
    }
}
