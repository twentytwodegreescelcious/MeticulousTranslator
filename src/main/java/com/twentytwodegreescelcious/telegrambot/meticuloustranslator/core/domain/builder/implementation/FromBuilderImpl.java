package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.From;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.builder.FromBuilder;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public class FromBuilderImpl implements FromBuilder {
    private From from;

    public FromBuilderImpl() {
        this.from = new From();
    }

    @Override
    public From build() {
        From from = new From();
        from.setId(this.from.getId());
        from.setBot(this.from.isBot());
        from.setFirstName(this.from.getFirstName());
        from.setLastName(this.from.getLastName());
        from.setUsername(this.from.getUsername());
        from.setLanguageCode(this.from.getLanguageCode());
        return from;
    }

    @Override
    public FromBuilder setId(Integer id) {
        this.from.setId(id);
        return this;
    }

    @Override
    public FromBuilder setBot(boolean isBot) {
        this.from.setBot(isBot);
        return this;
    }

    @Override
    public FromBuilder setFirstName(String firstName) {
        this.from.setFirstName(firstName);
        return this;
    }

    @Override
    public FromBuilder setLastName(String lastName) {
        this.from.setLastName(lastName);
        return this;
    }

    @Override
    public FromBuilder setUsername(String username) {
        this.from.setUsername(username);
        return this;
    }

    @Override
    public FromBuilder setLanguageCode(String languageCode) {
        this.from.setLanguageCode(languageCode);
        return this;
    }
}
