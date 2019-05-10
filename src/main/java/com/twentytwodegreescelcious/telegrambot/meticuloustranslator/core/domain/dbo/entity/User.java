package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity;

import javax.persistence.*;

/**
 * Created by twentytwodegreescelcious on 2/6/2019.
 */
@Entity
@Table(name = "mt_user")
public class User {

    @Id
    @Column(name = "chat_id", nullable = false)
    private Integer id;
    @Column(name = "default_language", length = 2, nullable = false)
    private String defaultLanguage;
    @Column(name="current_topic")
    private String currentTopic;
    @Column(name="is_dictation")
    private boolean isDictation = false;
    @Column(name = "dictation_topic")
    private String dictationTopic;


    public User() {
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

    public String getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(String currentTopic) {
        this.currentTopic = currentTopic;
    }

    public boolean isDictation() {
        return isDictation;
    }

    public void setDictation(boolean dictation) {
        isDictation = dictation;
    }

    public String getDictationTopic() {
        return dictationTopic;
    }

    public void setDictationTopic(String dictationTopic) {
        this.dictationTopic = dictationTopic;
    }
}
