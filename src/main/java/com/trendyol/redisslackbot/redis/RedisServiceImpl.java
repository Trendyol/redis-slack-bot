package com.trendyol.redisslackbot.redis;

import com.trendyol.redisslackbot.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl<V> implements RedisService<V> {

    private final RedisTemplate<String, V> redisTemplate;

    @Override
    @SuppressWarnings(value = "unchecked")
    public Set<V> listKeys() {
        try {
            log.info("Started list value");
            Set<V> keys = (Set<V>) redisTemplate.opsForValue().getOperations().keys("*");
            log.info("Finished list value");
            return keys;
        } catch (Exception e) {
            log.error("Encountered an exception while listing keys", e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Optional<V> get(String key) {
        try {
            log.info("Started get value by key: {}", key);
            Optional<V> result = Optional.ofNullable(redisTemplate.opsForValue().get(key));
            log.info("Finished get value by key: {}", key);
            return result;
        } catch (Exception e) {
            log.error("Encountered an exception while getting value", e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void set(String key, V value) {
        try {
            log.info("Started set value by key: {} , value: {}", key, value);
            redisTemplate.opsForValue().set(key, value);
            log.info("Finished set value by key: {} , value: {}", key, value);
        } catch (Exception e) {
            log.error("Encountered an exception while setting value", e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void set(String key, V value, long ttlInSeconds) {
        try {
            log.info("Started set value by key: {} , value: {} , ttlInSeconds : {}", key, value, ttlInSeconds);
            redisTemplate.opsForValue().set(key, value, ttlInSeconds, TimeUnit.SECONDS);
            log.info("Finished set value by key: {} , value: {} , ttlInSeconds : {}", key, value, ttlInSeconds);
        } catch (Exception e) {
            log.error("Encountered an exception while setting value", e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Boolean delete(String key) {
        try {
            log.info("Started delete value by key: {}", key);
            Boolean isDeleted = redisTemplate.delete(key);
            log.info("Finished delete value by key: {}", key);
            return isDeleted;
        } catch (Exception e) {
            log.error("Encountered an exception while deleting value", e);
            throw new BusinessException(e.getMessage());
        }
    }
}
