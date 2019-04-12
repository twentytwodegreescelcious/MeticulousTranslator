package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPair;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.WordPairQuizInfoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class WordPairServiceImplTest {

    @InjectMocks
    private WordPairServiceImpl wordPairService;

    @Mock
    private WordPairDao wordPairDao;

    @Mock
    private UserService userService;

    @Mock
    private WordPairQuizInfoService wordPairQuizInfoService;

    @Mock
    private MessageSource messageSource;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createWordPair() {
        WordPair wordPair = new WordPair();

        when(wordPairDao.save(anyObject())).thenReturn(wordPair);

        wordPairService.createWordPair(wordPair);

        verify(wordPairDao, times(1)).save(wordPair);
    }

    @Test
    public void createWordPairWithUserEqualToNull() {
        Integer chatId = 0;
        String wordPair = "Word - Pair";
        String messageDummy = "wordserviceimpl.createwordpair.notopic";

        when(userService.getUser(anyInt())).thenReturn(null);

        wordPairService.createWordPair(chatId, wordPair);

        verify(userService, times(1)).getUser(chatId);
        verify(messageSource, times(1)).getMessage(messageDummy, null, Locale.ENGLISH);
    }

    @Test
    public void createWordPairWithUsersCurrentTopicEqualToNull() {
        Integer chatId = 0;
        String wordPair = "Word - Pair";
        User user = new User();
        user.setDefaultLanguage("en");
        String messageDummy = "wordserviceimpl.createwordpair.notopic";

        when(userService.getUser(anyInt())).thenReturn(user);

        wordPairService.createWordPair(chatId, wordPair);

        verify(userService, times(1)).getUser(chatId);
        verify(messageSource, times(1)).getMessage(messageDummy, null, Locale.ENGLISH);
    }

    @Test
    public void createWordPairWithUserAndCurrentTopicNotNullWithCorrectWordAndTranslationStringThatDoesNotExist() {
        Integer chatId = 0;
        String wordPair = "Word - Pair";
        User user = new User();
        user.setCurrentTopic("Current topic");
        user.setDefaultLanguage("en");
        String messageDummy = "wordserviceimpl.createwordpair.success";
        String wordAndTranslationDummy[] = {"Word", "Pair"};
        WordPair wp = new WordPair(user, wordAndTranslationDummy[0], wordAndTranslationDummy[1], user.getCurrentTopic(),
                wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));

        when(userService.getUser(anyInt())).thenReturn(user);
        when(wordPairDao.findByWordAndTranslationAndTopicIgnoreCase(anyString(), anyString(), anyString())).thenReturn(null);

        wordPairService.createWordPair(chatId, wordPair);

        verify(messageSource, times(1)).getMessage(messageDummy,
                new Object[]{wordAndTranslationDummy[0], wordAndTranslationDummy[1], wp.getTopic()}, new Locale(user.getDefaultLanguage()));

    }

    @Test
    public void createWordPairWithUserAndCurrentTopicNotNullWithCorrectWordAndTranslationStringThatExists() {
        Integer chatId = 0;
        String wordPair = "Word - Pair";
        User user = new User();
        user.setDefaultLanguage("en");
        user.setCurrentTopic("Current topic");
        String messageDummy = "wordserviceimpl.createwordpair.alreadyexists";
        String wordAndTranslationDummy[] = {"Word", "Pair"};

        when(userService.getUser(anyInt())).thenReturn(user);
        when(wordPairDao.findByWordAndTranslationAndTopicIgnoreCase(anyString(), anyString(), anyString())).thenReturn(new WordPair());

        wordPairService.createWordPair(chatId, wordPair);


        verify(messageSource, times(1)).getMessage(messageDummy,
                new Object[]{wordAndTranslationDummy[0], wordAndTranslationDummy[1], user.getCurrentTopic()}, Locale.ENGLISH);
    }

    @Test
    public void createWordPairWithUserAndCurrentTopicNotNullWithWordAndTranslationStringThatContainsExtraHyphens() {
        Integer chatId = 0;
        String wordPair = "Word - Pair - Odd";
        User user = new User();
        user.setDefaultLanguage("en");
        user.setCurrentTopic("Current topic");
        String messageDummy = "wordserviceimpl.createwordpair.specialchar";

        when(userService.getUser(anyInt())).thenReturn(user);

        wordPairService.createWordPair(chatId, wordPair);

        verify(messageSource, times(1)).getMessage(messageDummy,
                null, new Locale(user.getDefaultLanguage()));
    }

    @Test
    public void createWordPairWithUserAndCurrentTopicNotNullWithWordAndTranslationStringThatContainsNoHyphens() {
        Integer chatId = 0;
        String wordPair = "Word Pair";
        User user = new User();
        user.setDefaultLanguage("en");
        user.setCurrentTopic("Current topic");
        String messageDummy = "wordserviceimpl.createwordpair.incorrectformat";

        when(userService.getUser(anyInt())).thenReturn(user);

        wordPairService.createWordPair(chatId, wordPair);

        verify(messageSource, times(1)).getMessage(messageDummy,
                null, new Locale(user.getDefaultLanguage()));
    }

    @Test
    public void getWordPairThatExists() {
        Integer id = 0;

        when(wordPairDao.existsById(anyInt())).thenReturn(true);

        wordPairService.getWordPair(id);

        verify(wordPairDao, times(1)).getOne(id);
    }

    @Test
    public void getWordPairThatDoesNotExist() {
        Integer id = 0;
        assertNull(wordPairService.getWordPair(id));
    }

    @Test
    public void editWordPair() {
        WordPair wp = new WordPair();

        wordPairService.editWordPair(wp);

        verify(wordPairDao, times(1)).save(wp);
    }

    @Test
    public void deleteWordPair() {
        WordPair wp = new WordPair();

        wordPairService.deleteWordPair(wp);

        verify(wordPairDao, times(1)).delete(wp);
    }

    @Test
    public void deleteWordPair1() {
        Integer id = 0;

        wordPairService.deleteWordPair(id);

        verify(wordPairDao, times(1)).deleteById(id);
    }

    @Test
    public void getAllWordPairs() {
        wordPairService.getAllWordPairs();

        verify(wordPairDao, times(1)).findAll();
    }

    @Test
    public void getWordPairsByTopicThatExistsAndContainsWords() {
        String topic = "Example";
        User user = new User();
        String wordAndTranslationDummy[] = {"Word", "Pair"};
        WordPair wp1 = new WordPair(user, wordAndTranslationDummy[0], wordAndTranslationDummy[1], user.getCurrentTopic(),
                wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));
        WordPair wp2 = new WordPair(user, wordAndTranslationDummy[0], wordAndTranslationDummy[1]
                + "1", user.getCurrentTopic() + "1",
                wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));
        List<WordPair> dummyList = new ArrayList<>();
        dummyList.add(wp1);
        dummyList.add(wp2);

        when(wordPairDao.findByTopicIgnoreCase(anyString())).thenReturn(dummyList);

        assertNotEquals("Sorry, but the requested topic '{0}' is empty or does not exist.", wordPairService.getWordPairs(topic));
    }

    @Test
    public void countWordPairs() {
        wordPairService.countWordPairs();
        verify(wordPairDao, times(1)).count();
    }

    @Test
    public void getTopics() {
        User user = new User();
        String wordAndTranslationDummy[] = {"Word", "Pair"};
        WordPair wp1 = new WordPair(user, wordAndTranslationDummy[0], wordAndTranslationDummy[1], "Topic1",
                wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));
        WordPair wp2 = new WordPair(user, wordAndTranslationDummy[0], wordAndTranslationDummy[1]
                + "1", "Topic10",
                wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));
        List<WordPair> dummyList = new ArrayList<>();
        dummyList.add(wp1);
        dummyList.add(wp2);
        List<String> dummyTopicList = new ArrayList<>();
        dummyTopicList.add("Topic1");
        dummyTopicList.add("Topic10");

        when(wordPairDao.findByUser(anyObject())).thenReturn(dummyList);

        assertEquals(dummyTopicList, wordPairService.getTopics(user));
    }

    @Test
    public void getTopicsByChatId() {
        User user = new User();
        String wordAndTranslationDummy[] = {"Word", "Pair"};
        WordPair wp1 = new WordPair(user, wordAndTranslationDummy[0], wordAndTranslationDummy[1], "Topic1",
                wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));
        WordPair wp2 = new WordPair(user, wordAndTranslationDummy[0], wordAndTranslationDummy[1]
                + "1", "Topic10",
                wordPairQuizInfoService.createWordPairQuizInfo(new WordPairQuizInfo()));
        List<WordPair> dummyList = new ArrayList<>();
        dummyList.add(wp1);
        dummyList.add(wp2);
        List<String> dummyTopicList = new ArrayList<>();
        dummyTopicList.add("Topic1");
        dummyTopicList.add("Topic10");

        when(userService.getUser(anyInt())).thenReturn(user);
        when(wordPairDao.findByUser(anyObject())).thenReturn(dummyList);

        assertEquals(dummyTopicList, wordPairService.getTopics(0));
    }
}