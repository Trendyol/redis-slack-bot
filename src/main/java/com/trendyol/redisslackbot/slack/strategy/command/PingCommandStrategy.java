package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.slack.SlackService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.PING;
import static com.trendyol.redisslackbot.slack.constant.SlackConstant.PING_MESSAGE;

@Service
@RequiredArgsConstructor
public class PingCommandStrategy implements SlackCommandStrategy {

    private final SlackService slackService;

    @Override
    public String getCommandName() {
        return PING;
    }

    @Override
    public void process(String textMessage) {
        slackService.sendPostMessage(PING_MESSAGE);
    }
}
