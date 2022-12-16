package com.trendyol.redisslackbot.slack.strategy;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.*;

@Service
public class SlackCommand {

    private final Map<String, SlackCommandStrategy> slackCommandStrategyMap = new HashMap<>();

    public SlackCommand(ListableBeanFactory beanFactory) {
        beanFactory.getBeansOfType(SlackCommandStrategy.class)
                .values()
                .forEach(service -> slackCommandStrategyMap.put(service.getCommandName(), service));
    }

    public void process(String commandName) {
        if (commandName.contains(REDIS_SLACK_BOT)) return;

        if (slackCommandStrategyMap.containsKey(commandName)) {
            slackCommandStrategyMap.get(commandName).process(commandName);
        } else if (commandName.contains(GET)) {
            slackCommandStrategyMap.get(GET).process(commandName);
        } else if (commandName.contains(SET)) {
            slackCommandStrategyMap.get(SET).process(commandName);
        } else if (commandName.contains(DELETE)) {
            slackCommandStrategyMap.get(DELETE).process(commandName);
        }
    }
}
