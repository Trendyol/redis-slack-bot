package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.base.BaseTest;
import com.trendyol.redisslackbot.slack.SlackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.HELP;
import static com.trendyol.redisslackbot.slack.constant.SlackConstant.HELP_MESSAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HelpCommandStrategyTest extends BaseTest {

    private HelpCommandStrategy helpCommandStrategy;

    @Mock
    private SlackService slackService;

    @BeforeEach
    void setUp() {
        helpCommandStrategy = new HelpCommandStrategy(slackService);
    }

    @Test
    void should_get_command_name() {
        //when
        String commandName = helpCommandStrategy.getCommandName();

        //then
        assertThat(commandName).isEqualTo(HELP);
    }

    @Test
    void should_process() {
        //given
        String textMessage = "HELP";

        //when
        helpCommandStrategy.process(textMessage);

        //then
        inOrder.verify(slackService).sendPostMessage(HELP_MESSAGE);
        inOrder.verifyNoMoreInteractions();
    }
}