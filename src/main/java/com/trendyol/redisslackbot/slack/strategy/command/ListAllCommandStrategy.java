package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.CACHE_IS_EMPTY;
import static com.trendyol.redisslackbot.slack.constant.SlackConstant.LIST_ALL;

@Service
@RequiredArgsConstructor
public class ListAllCommandStrategy implements SlackCommandStrategy {

    private final SlackService slackService;
    private final RedisService<String> stringRedisService;

    @Override
    public String getCommandName() {
        return LIST_ALL;
    }

    @Override
    public void process(String textMessage) {
        Set<String> keys = stringRedisService.listKeys();
        String postMessage = CACHE_IS_EMPTY;
        if (!keys.isEmpty()) {
            postMessage = StringUtils.join(keys, "\n");
        }
        slackService.sendPostMessage(postMessage);
    }
}
