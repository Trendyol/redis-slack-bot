package com.trendyol.redisslackbot.redis;

import java.util.Optional;
import java.util.Set;

public interface RedisService<V> {

    Set<V> listKeys();

    Optional<V> get(String key);

    void set(String key, V value);

    void set(String key, V value, long ttlInSeconds);

    Boolean delete(String key);
}
