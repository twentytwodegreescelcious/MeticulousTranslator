package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation;


import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Chat;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.From;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Message;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.MessageBuilder;

import java.util.Date;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public class MessageBuilderImpl implements MessageBuilder {

    private Message message;

    public MessageBuilderImpl() {
        message = new Message();
    }

    @Override
    public Message build() {
        Message message = new Message();
        message.setChat(this.message.getChat());
        message.setDate(this.message.getDate());
        message.setFrom(this.message.getFrom());
        message.setMessageId(this.message.getMessageId());
        message.setText(this.message.getText());
        return message;
    }

    @Override
    public MessageBuilder setMessageId(Integer messageId) {
        this.message.setMessageId(messageId);
        return this;
    }

    @Override
    public MessageBuilder setFrom(From from) {
        this.message.setFrom(from);
        return this;
    }

    @Override
    public MessageBuilder setChat(Chat chat) {
        this.message.setChat(chat);
        return this;
    }

    @Override
    public MessageBuilder setDate(Date date) {
        this.message.setDate(date);
        return this;
    }

    @Override
    public MessageBuilder setText(String text) {
        this.message.setText(text);
        return this;
    }
}
