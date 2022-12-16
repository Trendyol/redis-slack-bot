package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.base.BaseTest;
import com.trendyol.redisslackbot.slack.SlackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.PING;
import static com.trendyol.redisslackbot.slack.constant.SlackConstant.PING_MESSAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PingCommandStrategyTest extends BaseTest {

    private PingCommandStrategy pingCommandStrategy;

    @Mock
    private SlackService slackService;

    @BeforeEach
    void setUp() {
        pingCommandStrategy = new PingCommandStrategy(slackService);
    }

    @Test
    void should_get_command_name() {
        //when
        String commandName = pingCommandStrategy.getCommandName();

        //then
        assertThat(commandName).isEqualTo(PING);
    }

    @Test
    void should_process() {
        //given
        String textMessage = "PING";

        //when
        pingCommandStrategy.process(textMessage);

        //then
        inOrder.verify(slackService).sendPostMessage(PING_MESSAGE);
        inOrder.verifyNoMoreInteractions();
    }
}