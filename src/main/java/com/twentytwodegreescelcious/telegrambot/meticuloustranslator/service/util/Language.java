package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util;

/**
 * Created by twentytwodegreescelcious on 2/11/2019.
 */
public enum Language {
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

    Language(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static String find(String requested) throws LanguageNotFoundException {
        requested = requested.trim();
        if (ENGLISH.name().equalsIgnoreCase(requested)) {
            return ENGLISH.value;
        } else if (RUSSIAN.name().equalsIgnoreCase(requested) ||
                РУССКИЙ.name().equalsIgnoreCase(requested)) {
            return RUSSIAN.value;
        } else if (UKRAINIAN.name().equalsIgnoreCase(requested) ||
                УКРАЇНСЬКА.name().equalsIgnoreCase(requested)) {
            return UKRAINIAN.value;
        } else if (DEUTSCH.name().equalsIgnoreCase(requested) ||
                GERMAN.name().equalsIgnoreCase(requested)) {
            return GERMAN.value;
        } else if (FRENCH.name().equalsIgnoreCase(requested) ||
                FRANÇAISE.name().equalsIgnoreCase(requested)) {
            return FRENCH.value;
        }
        throw new LanguageNotFoundException();
    }
}
