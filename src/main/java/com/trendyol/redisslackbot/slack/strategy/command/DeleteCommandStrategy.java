package com.trendyol.redisslackbot.slack.strategy.command;

import com.trendyol.redisslackbot.redis.RedisService;
import com.trendyol.redisslackbot.slack.SlackService;
import com.trendyol.redisslackbot.slack.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.*;

@Service
@RequiredArgsConstructor
public class DeleteCommandStrategy implements SlackCommandStrategy {

    private final SlackService slackService;
    private final RedisService<String> stringRedisService;

    @Override
    public String getCommandName() {
        return DELETE;
    }

    @Override
    public void process(String textMessage) {
        String key = textMessage.replace(DELETE, REPLACEMENT);
        Boolean isDeleted = stringRedisService.delete(key);
        sendPostMessage(isDeleted);
    }

    private void sendPostMessage(Boolean isDeleted) {
        String textMessage;
        if (Boolean.TRUE.equals(isDeleted)) {
            textMessage = SUCCESSFULLY_DELETED;
        } else {
            textMessage = DELETING_CACHE_ERROR;
        }
        slackService.sendPostMessage(textMessage);
    }
}
