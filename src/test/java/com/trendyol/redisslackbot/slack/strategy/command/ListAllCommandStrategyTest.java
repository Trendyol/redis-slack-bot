package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.base.BaseTest;
import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Set;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.CACHE_IS_EMPTY;
import static com.trendyol.redisslackbot.slack.constant.SlackConstant.LIST_ALL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class ListAllCommandStrategyTest extends BaseTest {

    private ListAllCommandStrategy listAllCommandStrategy;

    @Mock
    private SlackService slackService;

    @Mock
    private RedisService<String> stringRedisService;

    @BeforeEach
    void setUp() {
        listAllCommandStrategy = new ListAllCommandStrategy(slackService, stringRedisService);
    }

    @Test
    void should_get_command_name() {
        //when
        String commandName = listAllCommandStrategy.getCommandName();

        //then
        assertThat(commandName).isEqualTo(LIST_ALL);
    }

    @Test
    void should_process() {
        //given
        String textMessage = "LIST ALL";
        Set<String> keys = Set.of("key1", "key2");
        String postMessage = StringUtils.join(keys, "\n");

        when(stringRedisService.listKeys()).thenReturn(keys);

        //when
        listAllCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).listKeys();
        inOrder.verify(slackService).sendPostMessage(postMessage);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void should_process_when_cache_is_empty() {
        //given
        String textMessage = "LIST ALL";

        when(stringRedisService.listKeys()).thenReturn(Collections.emptySet());

        //when
        listAllCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).listKeys();
        inOrder.verify(slackService).sendPostMessage(CACHE_IS_EMPTY);
        inOrder.verifyNoMoreInteractions();
    }
}