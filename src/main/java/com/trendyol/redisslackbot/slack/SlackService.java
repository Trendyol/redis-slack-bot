package com.trendyol.redisslackbot.slack;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.trendyol.redisslackbot.slack.config.SlackConfiguration;
import com.trendyol.redisslackbot.slack.properties.SlackProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.trendyol.redisslackbot.slack.constant.SlackConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackProperties slackProperties;
    private final SlackConfiguration slackConfiguration;

    public String retrieveConversationsHistory() {
        MethodsClient client = slackConfiguration.getClient();
        try {
            ConversationsHistoryResponse response = client.conversationsHistory(r -> r
                    .token(slackProperties.getToken())
                    .channel(slackProperties.getChannelId())
                    .limit(1));
            return response.getMessages().get(0).getText();
        } catch (IOException | SlackApiException e) {
            log.error("Encountered an exception while retrieving history", e);
            sendPostMessage(RETRIEVE_CONVERSATIONS_HISTORY_ERROR + " and detail message :" + e.getMessage());
            return NULL;
        }
    }

    public void sendPostMessage(String textMessage) {
        MethodsClient client = slackConfiguration.getClient();
        try {
            client.chatPostMessage(r -> r
                    .token(slackProperties.getToken())
                    .channel(slackProperties.getChannelId())
                    .username(slackProperties.getUsername())
                    .text(textMessage));
        } catch (IOException | SlackApiException e) {
            log.error("Encountered an exception while sending post message", e);
            sendPostMessage(SENDING_POST_MESSAGE_ERROR + " and detail message :" + e.getMessage());
        }
    }
}
