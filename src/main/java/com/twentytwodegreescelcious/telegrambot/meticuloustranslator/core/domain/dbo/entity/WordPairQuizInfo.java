package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "wordpair_quiz_info")
public class WordPairQuizInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "last_checked")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChecked = new Date();
    @Column(name = "was_correct")
    private Boolean wasCorrect = true;
    @Column(name = "times_incorrect")
    private Integer timesIncorrect = 0;
    @Column(name = "in_quiz")
    private Boolean inQuiz = false;
    @Column(name="is_current")
    private Boolean isCurrent = false;

    public WordPairQuizInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(Date lastChecked) {
        this.lastChecked = lastChecked;
    }

    public Boolean getWasCorrect() {
        return wasCorrect;
    }

    public void setWasCorrect(Boolean wasCorrect) {
        this.wasCorrect = wasCorrect;
    }

    public Integer getTimesIncorrect() {
        return timesIncorrect;
    }

    public void setTimesIncorrect(Integer timesIncorrect) {
        this.timesIncorrect = timesIncorrect;
    }

    public Boolean getInQuiz() {
        return inQuiz;
    }

    public void setInQuiz(Boolean inQuiz) {
        this.inQuiz = inQuiz;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }
}
