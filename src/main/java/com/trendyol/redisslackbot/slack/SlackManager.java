package com.trendyol.redisslackbot.slack;

import com.trendyol.redisslackbot.common.service.ProfileService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.NULL;

@Service
@RequiredArgsConstructor
public class SlackManager {

    private final SlackCommand slackCommand;
    private final SlackService slackService;
    private final ProfileService profileService;

    public void process() {
        if (profileService.isProd()) {
            String textMessage = slackService.retrieveConversationsHistory();
            if (!NULL.equals(textMessage)) {
                try {
                    slackCommand.process(textMessage);
                } catch (Exception e) {
                    slackService.sendPostMessage(e.getMessage());
                }
            }
        }
    }
}
