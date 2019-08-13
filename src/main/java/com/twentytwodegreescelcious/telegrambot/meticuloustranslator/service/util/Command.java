package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util;

/**
 * Created by twentytwodegreescelcious on 1/24/2019.
 */
public enum Command {
    greet("/greet"),
    translate("/translate"),
    start("/start"),
    setlanguage("/setlanguage"),
    availablelanguages("/availablelanguages"),
    newtopic("/newtopic"),
    closetopic("/closetopic"),
    addword("/addword"),
    getwordsfortopic("/getwordsfortopic"),
    startquiz("/startquiz"),
    next("/next"),
    finishquiz("/stop");

    public String value;

    Command(String value) {
        this.value = value;
    }
}