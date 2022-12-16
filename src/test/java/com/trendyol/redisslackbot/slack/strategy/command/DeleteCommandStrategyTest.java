package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.base.BaseTest;
import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class DeleteCommandStrategyTest extends BaseTest {

    private DeleteCommandStrategy deleteCommandStrategy;

    @Mock
    private SlackService slackService;

    @Mock
    private RedisService<String> stringRedisService;

    @BeforeEach
    void setUp() {
        deleteCommandStrategy = new DeleteCommandStrategy(slackService, stringRedisService);
    }

    @Test
    void should_get_command_name() {
        //when
        String commandName = deleteCommandStrategy.getCommandName();

        //then
        assertThat(commandName).isEqualTo(DELETE);
    }

    @Test
    void should_process_when_successfully_deleted() {
        //given
        String textMessage = "DELETE key";
        String key = "key";
        when(stringRedisService.delete(key)).thenReturn(true);

        //when
        deleteCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).delete(key);
        inOrder.verify(slackService).sendPostMessage(SUCCESSFULLY_DELETED);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void should_process_when_unsuccessfully_deleted() {
        //given
        String textMessage = "DELETE key";
        String key = "key";
        when(stringRedisService.delete(key)).thenReturn(false);

        //when
        deleteCommandStrategy.process(textMessage);

        //then
        inOrder.verify(stringRedisService).delete(key);
        inOrder.verify(slackService).sendPostMessage(DELETING_CACHE_ERROR);
        inOrder.verifyNoMoreInteractions();
    }
}