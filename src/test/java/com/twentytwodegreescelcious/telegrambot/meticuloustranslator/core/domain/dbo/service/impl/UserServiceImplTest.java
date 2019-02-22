package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.UserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.User;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.UserService;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service.util.Language;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createMTUser() {
        User user = new User();

        when(userDao.save(anyObject())).thenReturn(user);

        userService.createMTUser(user);

        verify(userDao, times(1)).save(user);
    }

    @Test
    public void getMTUser() {
        User user = new User();

        when(userDao.findById(anyInt())).thenReturn(Optional.of(user));

        userService.getMTUser(anyInt());

        verify(userDao, times(1)).findById(anyInt());
    }

    @Test
    public void editMTUser() {
        User user = new User();

        when(userDao.saveAndFlush(anyObject())).thenReturn(user);

        userService.editMTUser(user);

        verify(userDao, times(1)).saveAndFlush(anyObject());
    }

    @Test
    public void deleteMTUser() {
        User user = new User();

        userService.deleteMTUser(user);

        verify(userDao, times(1)).delete(anyObject());
    }

    @Test
    public void deleteMTUser1() {
        Integer id = 0;

        userService.deleteMTUser(id);

        verify(userDao, times(1)).deleteById(anyInt());
    }

    @Test
    public void getAllMTUsers() {
        List<User> users = new ArrayList<>();

        when(userDao.findAll()).thenReturn(users);

        userService.getAllMTUsers();

        verify(userDao, times(1)).findAll();
    }

    @Test
    public void countMTUsers() {
        Long counter = 0L;

        when(userDao.count()).thenReturn(counter);

        userService.countMTUsers();

        verify(userDao, times(1)).count();
    }

    @Test
    @Ignore
    public void setLanguageToAnExistingUser() {

        when(userService.getMTUser(anyInt())).thenReturn(new User());



        verify(userService, times(1)).getMTUser(anyInt());
        verify(messageSource, times(1)).getMessage(anyString(), anyObject(), anyObject());
        verify(userService, times(1)).editMTUser(anyObject());
    }

//    @Test
//    public void setLanguageToNonExistingUser() {
//        UserService userService = mock(UserServiceImpl.class);
//
//        when(userService.getMTUser(anyInt())).thenReturn(null);
//
//        verify(userService, times(1)).getMTUser(anyInt());
//        verify(messageSource, times(1)).getMessage(anyString(), anyObject(), anyObject());
//        verify(userService, times(1)).createMTUser(anyObject());
//    }
//
//    @Test
//    public void newTopicToAnExistingUserWithNoTopicSet() {
//        UserService userService = mock(UserServiceImpl.class);
//        User user = mock(User.class);
//
//        when(userService.getMTUser(anyInt())).thenReturn(user);
//
//        verify(user, times(1)).setCurrentTopic(anyString());
//        verify(userService, times(1)).editMTUser(user);
//        verify(messageSource, times(1)).getMessage(anyString(), anyObject(), anyObject());
//
//    }
//
//    @Test
//    public void newTopicToAnExistingUserWithATopicSet() {
//        UserService userService = mock(UserServiceImpl.class);
//        User user = mock(User.class);
//        user.setCurrentTopic(anyString());
//
//        when(userService.getMTUser(anyInt())).thenReturn(user);
//
//        verify(messageSource, times(1)).getMessage(anyString(), eq(null), anyObject());
//    }
//
//    @Test
//    public void newTopicToANonExistingUser() {
//        UserService userService = mock(UserServiceImpl.class);
//
//        when(userService.getMTUser(anyInt())).thenReturn(null);
//
//        verify(messageSource, times(1)).getMessage(anyString(), eq(null), anyObject());
//    }
//
//    @Test
//    public void closeTopicToAnExistingUserWithATopicSet() {
//        UserService userService = mock(UserServiceImpl.class);
//        User user = mock(User.class);
//        user.setCurrentTopic(anyString());
//
//        when(userService.getMTUser(anyInt())).thenReturn(user);
//
//        verify(user, times(1)).getCurrentTopic();
//        verify(user, times(1)).setCurrentTopic(null);
//        verify(messageSource, times(1)).getMessage(anyString(), anyObject(), anyObject());
//    }
//
//    @Test
//    public void closeTopicToAnExistingUserWithNoTopicSet() {
//        UserService userService = mock(UserServiceImpl.class);
//        User user = mock(User.class);
//        when(userService.getMTUser(anyInt())).thenReturn(user);
//
//        verify(messageSource, times(1)).getMessage(anyString(), eq(null), anyObject());
//    }
//
//    @Test
//    public void closeTopicToANonExistingUser() {
//        UserService userService = mock(UserServiceImpl.class);
//
//        when(userService.getMTUser(anyInt())).thenReturn(null);
//
//        verify(messageSource, times(1)).getMessage(anyString(), eq(null), anyObject());
//    }
}