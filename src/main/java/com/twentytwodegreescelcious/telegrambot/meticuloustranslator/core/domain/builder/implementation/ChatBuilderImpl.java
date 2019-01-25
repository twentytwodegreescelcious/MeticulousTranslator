package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Chat;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.ChatBuilder;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public class ChatBuilderImpl implements ChatBuilder {

    private Chat chat;

    public ChatBuilderImpl() {
        this.chat = new Chat();
    }

    @Override
    public Chat build() {
        Chat chat = new Chat();
        chat.setId(this.chat.getId());
        chat.setFirstName(this.chat.getFirstName());
        chat.setLastName(this.chat.getLastName());
        chat.setUsername(this.chat.getUsername());
        chat.setType(this.chat.getType());
        return chat;
    }

    @Override
    public ChatBuilder setId(Integer id) {
        this.chat.setId(id);
        return this;
    }

    @Override
    public ChatBuilder setFirstName(String firstName) {
        if (firstName != null) {
            this.chat.setFirstName(firstName);
        }
        return this;
    }

    @Override
    public ChatBuilder setLastName(String lastName) {
        if (lastName != null) {
            this.chat.setLastName(lastName);
        }
        return this;
    }

    @Override
    public ChatBuilder setUsername(String username) {
        if (username != null) {
            this.chat.setUsername(username);
        }
        return this;
    }

    @Override
    public ChatBuilder setType(String type) {
        this.chat.setType(type);
        return this;
    }
}
