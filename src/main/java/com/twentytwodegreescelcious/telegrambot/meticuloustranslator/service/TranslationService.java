package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by twentytwodegreescelcious on 1/8/2019.
 */
public interface TranslationService {
    String translate(String query, String defaultLanguage) throws IOException;
}
