package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.implementation;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairQuizInfoService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.UserQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by twentytwodegreescelcious on 2/7/2019.
 */
@Service
public class UserQuizServiceImpl implements UserQuizService {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WordPairDao wordPairDao;

    @Autowired
    private WordPairService wordPairService;

    @Autowired
    private WordPairQuizInfoService wordPairQuizInfoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String startQuiz(Integer id, String topic) {
        User user = userService.getUser(id);
        if (user != null) {
            if (user.isDictation()) {
                return messageSource.getMessage("userserviceimpl.newquiz.alreadyinquiz",
                        new Object[]{userService.getUser(user.getId())}, new Locale(user.getDefaultLanguage()));
            }
            List<WordPair> wordPairs = wordPairDao.findByTopicIgnoreCase(topic);
            if (null != wordPairs &&
                    !wordPairs.equals(Collections.emptyList()) && !wordPairs.isEmpty()) {
                wordPairs.get(0).getWordPairQuizInfo().setCurrent(true);
                for (WordPair wp : wordPairs) {
                    wp.getWordPairQuizInfo().setInQuiz(true);
                }
                user.setDictationTopic(topic);
                user.setDictation(true);
            }
            return messageSource.getMessage("userserviceimpl.newquiz.nosuchtopic", new Object[]{topic},
                    new Locale(user.getDefaultLanguage()));
        }
        return messageSource.getMessage("userserviceimpl.newquiz.nologin", null,
                Locale.ENGLISH);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String next(User user, String previousAnswer) {
        List<WordPair> wordPairs = wordPairService.getWordPairsForUserTopic(user, user.getDictationTopic());
        wordPairs = filterInQuiz(wordPairs);
        WordPair current;
        for (WordPair wp : wordPairs) {
            if (wp.getWordPairQuizInfo().getCurrent()) {
                validatePreviousAnswer(previousAnswer, wp, wp.getWord().equals(previousAnswer.trim()));
                break;
            }
        }
        if (!wordPairs.isEmpty()) {
            current = filterInQuiz(wordPairs).get(0);
            current.getWordPairQuizInfo().setCurrent(true);
            return "next, lol";
//            return messageSource.getMessage("userquizserviceimpl.next.sendnext", //TODO MESSAGESOURCE
//                    new Object[]{current.getTranslation()}, new Locale(user.getDefaultLanguage()));
        } else {
            return finishQuiz(user,false);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void validatePreviousAnswer(String previousAnswer, WordPair wp, boolean wasCorrect) {
        WordPairQuizInfo previous;
        if (wp.getWord().equals(previousAnswer.trim())) {
            previous = wp.getWordPairQuizInfo();
            previous.setLastChecked(new Date());
            previous.setCurrent(false);
            previous.setInQuiz(false);
            previous.setWasCorrect(wasCorrect);
            wp.setWordPairQuizInfo(previous);
            wordPairService.editWordPair(wp);
            wordPairQuizInfoService.updateWordPairQuizInfo(previous);
            return;
        }
    }

    private List<WordPair> filterInQuiz(List<WordPair> wordPairs) {
        List<WordPair> inQuiz = new ArrayList<>();
        for (WordPair wp : wordPairs) {
            if (wp.getWordPairQuizInfo().getInQuiz()) {
                inQuiz.add(wp);
            }
        }
        return inQuiz;
    }

    private String prepareQuizResult(User user, List<WordPair> wordPairs) {
        Integer correct = 0;
        for (WordPair wp : wordPairs) {
            if (wp.getWordPairQuizInfo().getWasCorrect()) {
                correct++;
            }
        }
        return "Your result is " + correct + "out of " + wordPairs.size(); //TODO MESSAGESOURCE
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String finishQuiz(User user, boolean isForceQuit) {
        List<WordPair> wordPairs;
        if (isForceQuit) {
            wordPairs = wordPairService.getAnsweredWordPairsForUserTopic(user, user.getDictationTopic());
        } else {
            wordPairs = wordPairService.getWordPairsForUserTopic(user, user.getDictationTopic());
        }
        user.setDictationTopic(null);
        return prepareQuizResult(user, wordPairs);
    }
}
