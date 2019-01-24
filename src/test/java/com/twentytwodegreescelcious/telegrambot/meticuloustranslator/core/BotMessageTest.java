package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core;

import com.mashape.unirest.http.HttpResponse;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareEverythingForTest;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.*;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

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