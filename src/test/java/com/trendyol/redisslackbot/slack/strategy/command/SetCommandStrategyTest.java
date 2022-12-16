package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.base.BaseTest;
import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SetCommandStrategyTest extends BaseTest {

    private SetCommandStrategy setCommandStrategy;

    @Mock
    private SlackService slackService;

    @Mock
    private RedisService<String> stringRedisService;

    @BeforeEach
    void setUp() {
        setCommandStrategy = new SetCommandStrategy(slackService, stringRedisService);
    }

    @Test
    void should_get_command_name() {
        //when
        String commandName = setCommandStrategy.getCommandName();

        //then
        assertThat(commandName).isEqualTo(SET);
    }

    @Test
    void should_process() {
        //given
        String textMessage = "SET key value";
        String key = "key";
        String value = "value";

        //when
        setCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).set(key, value);
        inOrder.verify(slackService).sendPostMessage(SUCCESSFULLY_ADDED);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void should_process_with_ttl() {
        //given
        String textMessage = "SET key value 30";
        String key = "key";
        String value = "value";
        String ttl = "30";

        //when
        setCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).set(key, value, Long.parseLong(ttl));
        inOrder.verify(slackService).sendPostMessage(SUCCESSFULLY_ADDED);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void should_process_when_text_message_is_not_valid() {
        //given
        String textMessage = "SET key value 30 q";

        //when
        setCommandStrategy.process(textMessage);

        //then
        inOrder.verify(slackService).sendPostMessage(SETTING_CACHE_ERROR);
        inOrder.verifyNoMoreInteractions();
    }
}