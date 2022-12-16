package com.trendyol.redisslackbot.slack.config;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SlackConfiguration {

    @Bean
    public MethodsClient getClient() {
        return Slack.getInstance().methods();
    }
}
