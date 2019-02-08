package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity;

import javax.persistence.*;

/**
 * Created by twentytwodegreescelcious on 2/6/2019.
 */
@Entity
@Table(name = "mt_user")
public class MTUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false)
    private Integer id;
    @Column(name = "default_language", length = 2, nullable = false)
    private String defaultLanguage;

    public MTUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }
}
