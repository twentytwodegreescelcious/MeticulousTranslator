package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by twentytwodegreescelcious on 2/6/2019.
 */
@Entity
@Table(name = "word_pair")
public class WordPair {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private User user;
    @Column(name = "word", nullable = false)
    private String word;
    @Column(name = "translation", nullable = false)
    private String translation;
    @Column(name = "topic", nullable = false)
    private String topic;
    @OneToOne
    @JoinColumn(name="wordpair_quiz_info_id")
    private WordPairQuizInfo wordPairQuizInfo;

    public WordPair() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public WordPairQuizInfo getWordPairQuizInfo() {
        return wordPairQuizInfo;
    }

    public void setWordPairQuizInfo(WordPairQuizInfo wordPairQuizInfo) {
        this.wordPairQuizInfo = wordPairQuizInfo;
    }
}
