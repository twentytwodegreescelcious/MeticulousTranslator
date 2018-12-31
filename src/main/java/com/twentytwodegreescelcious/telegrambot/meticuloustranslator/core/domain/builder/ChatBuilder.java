package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Chat;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public interface ChatBuilder {
    Chat build();
    ChatBuilder setId(Integer id);
    ChatBuilder setFirstName(String firstName);
    ChatBuilder setLastName(String lastName);
    ChatBuilder setUsername(String username);
    ChatBuilder setType(String type);
}
