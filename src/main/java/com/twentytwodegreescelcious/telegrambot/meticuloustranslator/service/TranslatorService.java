package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

import java.util.List;

public class TranslatorService {

    private GoogleTranslatorService googleTranslatorService;

    public TranslatorService() {
        googleTranslatorService = new GoogleTranslatorService();
    }

    public List<Translation> getTranslations(String request) {
        List<Translation> translations = googleTranslatorService.getTranslationsFor(request);
        if (!translations.isEmpty()) {

        }
        return translations;
    }
}