package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class From {

    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "is_bot")
    private boolean isBot;
    @JsonProperty(value="first_name")
    private String firstName;
    @JsonProperty(value="last_name")
    private String lastName;
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "language_code")
    private String languageCode;

    public int getId() {
        return id;
    }

    public boolean isBot() {
        return isBot;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getLanguageCode() {
        return languageCode;
    }
}
