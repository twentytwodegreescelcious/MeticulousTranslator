package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.net;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.BotMessage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpClientTest {

    private MockMvc mockMvc;
    private static final String URL = "https://api.telegram.org/bot768358876:AAERZhiezrmKkg0m6B8fDy3il0ry4KIflZk";

    @InjectMocks
    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(httpClient).build();
    }

    @Test
    @Ignore
    public void testMessageSending() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/sendMessage", new BotMessage(332082743, "Hello")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}