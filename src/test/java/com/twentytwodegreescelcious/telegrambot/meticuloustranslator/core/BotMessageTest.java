package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class BotMessageTest {

    @Test
    public void send() {
        new BotMessage(332082743, "hello from test");
    }
}