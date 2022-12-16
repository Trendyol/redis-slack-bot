package com.trendyol.redisslackbot.slack;

import com.trendyol.redisslackbot.base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class SlackCronJobUnitTest extends BaseTest {

    private SlackCronJob slackCronJob;

    @Mock
    private SlackManager slackManager;

    @BeforeEach
    void setUp() {
        slackCronJob = new SlackCronJob(slackManager);
    }

    @Test
    void should_fetch_last_messages_from_channel() {
        //when
        slackCronJob.fetchLastMessagesFromChannel();

        //then
        inOrder.verify(slackManager).process();
    }
}