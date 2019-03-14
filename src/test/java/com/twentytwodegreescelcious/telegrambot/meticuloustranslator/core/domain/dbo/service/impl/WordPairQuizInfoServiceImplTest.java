package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.WordPairQuizInfoDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.WordPairQuizInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class WordPairQuizInfoServiceImplTest {

    @InjectMocks
    private WordPairQuizInfoServiceImpl wordPairQuizInfoService;

    @Mock
    private WordPairQuizInfoDao wordPairQuizInfoDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void wordPairQuizInfoCreationTest() {
        WordPairQuizInfo wordPairQuizInfo = new WordPairQuizInfo();
        wordPairQuizInfo.setId(0);

        when(wordPairQuizInfoDao.save(anyObject())).thenReturn(wordPairQuizInfo);

        wordPairQuizInfoService.createWordPairQuizInfo(wordPairQuizInfo);

        verify(wordPairQuizInfoDao, times(1)).save(wordPairQuizInfo);
    }


    @Test
    public void existingWordPairQuizInfoReadingTest() {
        WordPairQuizInfo wordPairQuizInfo = new WordPairQuizInfo();
        Integer id = 0;

        when(wordPairQuizInfoDao.existsById((Integer)anyObject())).thenReturn(true);

        wordPairQuizInfoService.getWordPairQuizInfo(id);

        verify(wordPairQuizInfoDao, times(1)).existsById(id);
    }

    @Test
    public void nonExistingWordPairQuizInfoReadingTest() {
        when(wordPairQuizInfoDao.existsById((Integer)anyObject())).thenReturn(false);

        assertNull(wordPairQuizInfoService.getWordPairQuizInfo(0));
    }

    @Test
    public void WordPairQuizInfoDeletionTest() {
        Integer id = 0;

        wordPairQuizInfoService.deleteWordPairQuizInfo(0);

        verify(wordPairQuizInfoDao, times(1)).deleteById(id);
    }

    @Test
    public void WordPairQuizInfoDeletionObjectTest() {
        WordPairQuizInfo wordPairQuizInfo = new WordPairQuizInfo();

        wordPairQuizInfoService.deleteWordPairQuizInfo(wordPairQuizInfo);

        verify(wordPairQuizInfoDao, times(1)).delete(wordPairQuizInfo);
    }
}