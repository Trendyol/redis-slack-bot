package com.trendyol.redisslackbot.slack.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SlackConstant {

    public static final String REDIS_SLACK_BOT = "Redis Slack Bot";
    public static final String LIST_ALL = "LIST ALL";
    public static final String GET = "GET ";
    public static final String SET = "SET ";
    public static final String DELETE = "DELETE ";
    public static final String HELP = "HELP";
    public static final String PING = "PING";
    public static final String NULL = "NULL";
    public static final String REPLACEMENT = "";
    public static final String SUCCESSFULLY_ADDED = "Successfully added. :party_blob:";
    public static final String SUCCESSFULLY_DELETED = "Successfully deleted. :party_blob:";
    public static final String RETRIEVE_CONVERSATIONS_HISTORY_ERROR = ":alert: Encountered an exception while retrieving conversations history! :alert-blue:";
    public static final String SENDING_POST_MESSAGE_ERROR = ":alert: Encountered an exception while sending post message! :alert-blue:";
    public static final String DELETING_CACHE_ERROR = ":alert: Encountered an exception while deleting cache error! :alert-blue:";
    public static final String SETTING_CACHE_ERROR = ":alert: Encountered an exception while setting cache error! :alert-blue:";
    public static final String CACHE_IS_EMPTY = "Cache is empty.";
    public static final String PING_MESSAGE = "What can i help you ?";
    public static final String HELP_MESSAGE = "Redis Slack Bot\n" +
            "\n" +
            "\n" +
            "Overview \n" +
            "As Trendyol team we developed a new Slack bot to improve the usability of Redis. \n" +
            "\n" +
            "\n" +
            "With the help of the Redis Slack bot, you can:\n" +
            "- GET key -> Returns the value of the key.\n" +
            "- SET key value -> Sets or updates a key's value in the cache. The ttl is not given, so it remains in the cache indefinitely.\n" +
            "- SET key value ttl -> Sets or updates key's value which is in the cache. The ttl is given, so it stays in the cache for the number of seconds you specify.\n" +
            "- DELETE key -> If the key is in the cache, it is deleted.\n" +
            "- LIST ALL -> Displays a list of all keys in the cache.\n" +
            "- HELP -> How to use redis slack bot ?\n" +
            "- PING -> What can i help you ?\n" +
            "\n" +
            "\n" +
            "The following are some examples of each command:\n" +
            "- GET my_key_123\n" +
            "- SET my_key_123 \"success\"\n" +
            "- SET my_key_123 \"{\"name\":\"John\",\"age\":30,\"car\":null}\"\n" +
            "- SET my_key_123 \"{\"name\":\"John\",\"age\":30,\"car\":null}\" 30\n" +
            "- SET my_key_123 \"success\" 30\n" +
            "- DELETE my_key_123\n" +
            "- LIST ALL\n" +
            "- HELP \n" +
            "- PING \n";
}
