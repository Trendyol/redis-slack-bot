package com.trendyol.redisslackbot.slack;

import com.trendyol.redisslackbot.base.BaseTest;
import com.trendyol.redisslackbot.common.exception.BusinessException;
import com.trendyol.redisslackbot.common.service.ProfileService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.NULL;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class SlackManagerTest extends BaseTest {

    private SlackManager slackManager;

    @Mock
    private SlackCommand slackCommand;

    @Mock
    private SlackService slackService;

    @Mock
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        slackManager = new SlackManager(slackCommand, slackService, profileService);
    }

    @Test
    void should_process() {
        //given
        String textMessage = "text message";
        when(profileService.isProd()).thenReturn(true);
        when(slackService.retrieveConversationsHistory()).thenReturn(textMessage);

        //when
        slackManager.process();

        //then
        inOrder.verify(profileService).isProd();
        inOrder.verify(slackService).retrieveConversationsHistory();
        inOrder.verify(slackCommand).process(textMessage);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void should_process_when_text_message_is_null() {
        //given
        when(profileService.isProd()).thenReturn(true);
        when(slackService.retrieveConversationsHistory()).thenReturn(NULL);

        //when
        slackManager.process();

        //then
        inOrder.verify(profileService).isProd();
        inOrder.verify(slackService).retrieveConversationsHistory();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void should_process_when_active_profile_is_not_equal_to_prod() {
        //given
        when(profileService.isProd()).thenReturn(false);

        //when
        slackManager.process();

        //then
        inOrder.verify(profileService).isProd();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void throw_exception() {
        //given
        String textMessage = "text message";
        when(profileService.isProd()).thenReturn(true);
        when(slackService.retrieveConversationsHistory()).thenReturn(textMessage);
        Exception exception = new BusinessException("exception");
        doThrow(exception)
                .doNothing().when(slackCommand).process(textMessage);

        //when
        slackManager.process();

        //then
        inOrder.verify(profileService).isProd();
        inOrder.verify(slackService).retrieveConversationsHistory();
        inOrder.verify(slackCommand).process(textMessage);
        inOrder.verify(slackService).sendPostMessage(exception.getMessage());
        inOrder.verifyNoMoreInteractions();
    }
}