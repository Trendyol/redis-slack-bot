package com.trendyol.redisslackbot.slack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SlackCronJobIntegrationTest {

    @SpyBean
    private SlackCronJob slackCronJob;

    @Test
    void should_fetch_last_messages_from_channel() {
        await()
                .atMost(Duration.of(10, ChronoUnit.SECONDS))
                .untilAsserted(() -> verify(slackCronJob, atLeast(2)).fetchLastMessagesFromChannel());
    }
}