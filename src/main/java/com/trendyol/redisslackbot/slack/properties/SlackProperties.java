package com.trendyol.redisslackbot.slack.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {

    private String token;
    private String channelId;
    private String username;
}
