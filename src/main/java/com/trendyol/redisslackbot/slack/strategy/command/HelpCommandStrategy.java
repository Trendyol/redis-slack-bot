package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.slack.SlackService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.HELP;
import static com.trendyol.redisslackbot.slack.constant.SlackConstant.HELP_MESSAGE;

@Service
@RequiredArgsConstructor
public class HelpCommandStrategy implements SlackCommandStrategy {

    private final SlackService slackService;

    @Override
    public String getCommandName() {
        return HELP;
    }

    @Override
    public void process(String textMessage) {
        slackService.sendPostMessage(HELP_MESSAGE);
    }
}
