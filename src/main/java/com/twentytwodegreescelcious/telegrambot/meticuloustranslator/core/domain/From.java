package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String firstName;
    @JsonProperty(value="last_name")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String lastName;
    @JsonProperty(value = "username")
    @JsonIgnoreProperties(ignoreUnknown = true)
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

    public void setId(int id) {
        this.id = id;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
