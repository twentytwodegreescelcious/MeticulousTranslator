package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpClient.class)
public class BotMessageTest {

    @Test
    public void testMessageSending() throws Exception {
        mockStatic(HttpClient.class);

        BotMessage botMessage = new BotMessage(0, "test");
        botMessage.send();

        verifyStatic(Mockito.times(1));
        HttpClient.POST(anyString(), anyObject());
    }
}