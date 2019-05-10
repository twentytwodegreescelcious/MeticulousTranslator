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
import java.util.Objects;

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

    public WordPair(User user, String word, String translation, String topic, WordPairQuizInfo wordPairQuizInfo) {
        this.user = user;
        this.word = word;
        this.translation = translation;
        this.topic = topic;
        this.wordPairQuizInfo = wordPairQuizInfo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordPair wordPair = (WordPair) o;
        return Objects.equals(id, wordPair.id) &&
                Objects.equals(user, wordPair.user) &&
                Objects.equals(word, wordPair.word) &&
                Objects.equals(translation, wordPair.translation) &&
                Objects.equals(topic, wordPair.topic) &&
                Objects.equals(wordPairQuizInfo, wordPair.wordPairQuizInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, word, translation, topic, wordPairQuizInfo);
    }
}
