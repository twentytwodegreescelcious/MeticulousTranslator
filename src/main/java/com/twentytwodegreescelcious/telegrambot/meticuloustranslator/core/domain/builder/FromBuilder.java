package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.From;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public interface FromBuilder {
    From build();
    FromBuilder setId(Integer id);
    FromBuilder setBot(boolean isBot);
    FromBuilder setFirstName(String firstName);
    FromBuilder setLastName(String lastName);
    FromBuilder setUsername(String username);
    FromBuilder setLanguageCode(String languageCode);
}
