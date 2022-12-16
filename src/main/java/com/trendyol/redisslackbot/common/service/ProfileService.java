package com.trendyol.redisslackbot.common.service;

import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    public boolean isProd() {
        String springProfilesActive = System.getenv("SPRING_PROFILES_ACTIVE");
        return "prod".equals(springProfilesActive);
    }
}
