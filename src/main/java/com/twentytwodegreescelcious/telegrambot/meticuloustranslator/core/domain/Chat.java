package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class Chat {
    @JsonProperty(value="id")
    private int id;
    @JsonProperty(value = "first_name")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String firstName;
    @JsonProperty(value = "last_name")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String lastName;
    @JsonProperty(value = "username")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String username;
    @JsonProperty(value = "type")
    private String type;

    public int getId() {
        return id;
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

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setType(String type) {
        this.type = type;
    }
}
