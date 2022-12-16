package com.trendyol.redisslackbot.slack;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackCronJob {

    private final SlackManager slackManager;

    @Scheduled(cron = "*/5 * * * * *", zone = "Europe/Istanbul") //Every 5 seconds
    public void fetchLastMessagesFromChannel() {
        slackManager.process();
    }
}
