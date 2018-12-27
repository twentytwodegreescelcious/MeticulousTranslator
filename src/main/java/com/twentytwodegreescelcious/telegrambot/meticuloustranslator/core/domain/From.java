package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

public class From {

    private int id;
    private boolean is_bot;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;

    public int getId() {
        return id;
    }

    public boolean isIs_bot() {
        return is_bot;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public String getLanguage_code() {
        return language_code;
    }
}
