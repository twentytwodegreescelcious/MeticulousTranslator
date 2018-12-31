package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by twentytwodegreescelcious on 28.12.2018.
 */

public class Message {

    @JsonProperty(value = "message_id")
    private int messageId;
    @JsonProperty(value = "from")
    private From from;
    @JsonProperty(value = "chat")
    private Chat chat;
    @JsonProperty(value = "date")
    private Date date;
    @JsonProperty(value = "text")
    private String text;

    public int getMessageId() {
        return messageId;
    }

    public From getFrom() {
        return from;
    }

    public Chat getChat() {
        return chat;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }
}
