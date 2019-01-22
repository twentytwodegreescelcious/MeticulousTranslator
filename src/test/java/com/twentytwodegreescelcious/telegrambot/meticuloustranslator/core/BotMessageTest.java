package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.mashape.unirest.http.HttpResponse;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BotMessageTest {


    @Test
    public void testMessageSending() {
        HttpClient httpClient = mock(HttpClient.class);

        BotMessage botMessage = new BotMessage(0, "test");
        botMessage.send();

        verify(httpClient, atLeastOnce())
                .POST("https://api.telegram.org/bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk/sendMessage",
                        botMessage);
    }
}