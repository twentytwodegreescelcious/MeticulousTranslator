package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

import java.io.IOException;

/**
 * Created by twentytwodegreescelcious on 1/8/2019.
 */
public interface TranslationService {
    /**
     * Uses an API to translate given String.
     *
     * @param query
     * @param defaultLanguage
     * @return
     * @throws IOException
     */
    String translate(String query, String defaultLanguage) throws IOException;
}
