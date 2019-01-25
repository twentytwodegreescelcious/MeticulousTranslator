package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.command.BotCommand;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BotCommandExecutorTest {


    @Test
    public void testThatCommandHasBeenExecuted() {
        BotCommand botCommand = mock(BotCommand.class);

        new BotCommandExecutor().executeCommand(botCommand);

        verify(botCommand, atLeastOnce()).execute();
    }
}