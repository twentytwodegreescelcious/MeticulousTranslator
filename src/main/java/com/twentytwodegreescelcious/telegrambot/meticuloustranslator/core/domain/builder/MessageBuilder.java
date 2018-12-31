package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Chat;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.From;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Message;

import java.util.Date;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public interface MessageBuilder {
    Message build();
    MessageBuilder setMessageId(Integer messageId);
    MessageBuilder setFrom(From from);
    MessageBuilder setChat(Chat chat);
    MessageBuilder setDate(Date date);
    MessageBuilder setText(String text);
}
