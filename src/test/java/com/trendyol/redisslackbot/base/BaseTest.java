package com.trendyol.redisslackbot.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BaseTest {

    protected InOrder inOrder;

    @BeforeEach
    void setUpInOrder() {
        inOrder = inOrder(Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> (field.isAnnotationPresent(Mock.class) || field.isAnnotationPresent(Spy.class)))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(this);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray());
    }
}
