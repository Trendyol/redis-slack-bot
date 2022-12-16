package com.trendyol.redisslackbot.common.exception.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailDTO implements Serializable {

    private String key;
    private String message;
    private String errorCode;
    private String[] args;
}
