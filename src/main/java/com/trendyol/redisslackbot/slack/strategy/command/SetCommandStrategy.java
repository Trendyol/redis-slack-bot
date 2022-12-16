package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.*;

@Service
@RequiredArgsConstructor
public class SetCommandStrategy implements SlackCommandStrategy {

    private final SlackService slackService;
    private final RedisService<String> stringRedisService;

    @Override
    public String getCommandName() {
        return SET;
    }

    @Override
    public void process(String textMessage) {
        String[] strings = textMessage.replace(SET, REPLACEMENT).split("\\s");
        String postMessage = SETTING_CACHE_ERROR;
        if (strings.length == 2) {
            String key = strings[0];
            String value = strings[1];
            stringRedisService.set(key, value);
            postMessage = SUCCESSFULLY_ADDED;
        } else if (strings.length == 3) {
            String key = strings[0];
            String value = strings[1];
            String ttl = strings[2];
            stringRedisService.set(key, value, Long.parseLong(ttl));
            postMessage = SUCCESSFULLY_ADDED;
        }
        slackService.sendPostMessage(postMessage);
    }
}
