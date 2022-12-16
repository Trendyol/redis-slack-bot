package com.trendyol.redisslackbot.slack.strategy;

public interface SlackCommandStrategy {

    String getCommandName();

    void process(String textMessage);
}
