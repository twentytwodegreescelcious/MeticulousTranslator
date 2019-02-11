package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util;

import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by twentytwodegreescelcious on 2/11/2019.
 */
public enum Languages {
    ENGLISH("en"),

    RUSSIAN("ru"),
    РУССКИЙ("ru"),

    УКРАЇНСЬКА("uk"),
    UKRAINIAN("uk"),

    GERMAN("de"),
    DEUTSCH("de"),

    FRANÇAISE("fr"),
    FRENCH("fr");

    private String value;

    Languages(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static String find(String requested) throws LanguageNotFoundException {
        if (requested.trim().equalsIgnoreCase(ENGLISH.name().trim())) {
            return ENGLISH.value;
        } else if (requested.trim().equalsIgnoreCase(RUSSIAN.name().trim()) ||
                requested.trim().equalsIgnoreCase(РУССКИЙ.name().trim())) {
            return RUSSIAN.value;
        } else if (requested.trim().equalsIgnoreCase(UKRAINIAN.name().trim()) ||
                requested.trim().trim().equalsIgnoreCase(УКРАЇНСЬКА.name().trim())) {
            return UKRAINIAN.value;
        } else if (requested.trim().equalsIgnoreCase(DEUTSCH.name().trim()) ||
                requested.trim().equalsIgnoreCase(GERMAN.name().trim())) {
            return GERMAN.value;
        } else if (requested.trim().equalsIgnoreCase(FRENCH.name().trim()) ||
                requested.trim().equalsIgnoreCase(FRANÇAISE.name().trim())) {
            return FRENCH.value;
        } else {
            throw new LanguageNotFoundException();
        }
    }
}
