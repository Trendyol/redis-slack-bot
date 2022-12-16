package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.base.BaseTest;
import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.GET;
import static com.trendyol.redisslackbot.slack.constant.SlackConstant.NULL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class GetCommandStrategyTest extends BaseTest {

    private GetCommandStrategy getCommandStrategy;

    @Mock
    private SlackService slackService;

    @Mock
    private RedisService<String> stringRedisService;

    @BeforeEach
    void setUp() {
        getCommandStrategy = new GetCommandStrategy(slackService, stringRedisService);
    }

    @Test
    void should_get_command_name() {
        //when
        String commandName = getCommandStrategy.getCommandName();

        //then
        assertThat(commandName).isEqualTo(GET);
    }

    @Test
    void should_process() {
        //given
        String textMessage = "GET key";
        String key = "key";
        String value = "value";
        when(stringRedisService.get(key)).thenReturn(Optional.of(value));

        //when
        getCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).get(key);
        inOrder.verify(slackService).sendPostMessage(value);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void should_process_when_key_is_not_found() {
        //given
        String textMessage = "GET key";
        String key = "key";
        when(stringRedisService.get(key)).thenReturn(Optional.empty());

        //when
        getCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).get(key);
        inOrder.verify(slackService).sendPostMessage(NULL);
        inOrder.verifyNoMoreInteractions();
    }
}