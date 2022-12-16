package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.*;

@Service
@RequiredArgsConstructor
public class GetCommandStrategy implements SlackCommandStrategy {

    private final SlackService slackService;
    private final RedisService<String> stringRedisService;

    @Override
    public String getCommandName() {
        return GET;
    }

    @Override
    public void process(String textMessage) {
        String key = textMessage.replace(GET, REPLACEMENT);
        String value = stringRedisService.get(key).orElse(NULL);
        slackService.sendPostMessage(value);
    }
}
