package com.trendyol.redisslackbot.redis;

import com.trendyol.redisslackbot.common.exception.BusinessException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisServiceImplTest {

    private RedisServiceImpl<String> redisService;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        redisService = new RedisServiceImpl<>(redisTemplate);
    }

    @Test
    void should_list_all() {
        //given
        ValueOperations valueOperations = mock(ValueOperations.class);
        RedisOperations redisOperations = mock(RedisOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.getOperations()).thenReturn(redisOperations);

        Set<String> keys = Set.of("key1", "key2", "key3");
        when(valueOperations.getOperations().keys("*")).thenReturn(keys);

        //when
        Set<String> resultKeys = redisService.listKeys();

        //then
        assertThat(resultKeys).hasSize(3);
        assertThat(resultKeys).containsAll(Set.of("key1", "key2", "key3"));

        InOrder inOrder = inOrder(redisTemplate, valueOperations);
        inOrder.verify(redisTemplate).opsForValue();
        inOrder.verify(valueOperations).getOperations();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void throw_business_exception_for_list_all() {
        //given
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        Exception exception = new BusinessException("exception");
        when(valueOperations.getOperations()).thenThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> redisService.listKeys());

        //then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Business exception occurred Business exception occurred exception  ");
    }

    @Test
    void should_get() {
        //given
        ValueOperations valueOperations = mock(ValueOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("key")).thenReturn("value");

        //when
        Optional<String> optionalValue = redisService.get("key");

        //then
        assertThat(optionalValue).contains("value");

        InOrder inOrder = inOrder(redisTemplate, valueOperations);
        inOrder.verify(redisTemplate).opsForValue();
        inOrder.verify(valueOperations).get("key");
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void throw_business_exception_for_get() {
        //given
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        Exception exception = new BusinessException("exception");
        when(valueOperations.get("key")).thenThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> redisService.get("key"));

        //then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Business exception occurred Business exception occurred exception  ");
    }

    @Test
    void should_set() {
        //given
        ValueOperations valueOperations = mock(ValueOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        //when
        redisService.set("key", "value");

        //then
        InOrder inOrder = inOrder(redisTemplate, valueOperations);
        inOrder.verify(redisTemplate).opsForValue();
        inOrder.verify(valueOperations).set("key", "value");
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void throw_business_exception_for_set() {
        //given
        Exception exception = new BusinessException("exception");
        when(redisTemplate.opsForValue()).thenThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> redisService.set("key", "value"));

        //then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Business exception occurred Business exception occurred exception  ");
    }

    @Test
    void should_set_with_ttl() {
        //given
        ValueOperations valueOperations = mock(ValueOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        //when
        redisService.set("key", "value", 3);

        //then
        InOrder inOrder = inOrder(redisTemplate, valueOperations);
        inOrder.verify(redisTemplate).opsForValue();
        inOrder.verify(valueOperations).set("key", "value", 3, TimeUnit.SECONDS);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void throw_business_exception_for_set_ttl() {
        //given
        Exception exception = new BusinessException("exception");
        when(redisTemplate.opsForValue()).thenThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> redisService.set("key", "value", 3));

        //then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Business exception occurred Business exception occurred exception  ");
    }

    @Test
    void should_delete() {
        //given
        when(redisTemplate.delete("key")).thenReturn(true);

        //when
        Boolean isDeleted = redisService.delete("key");

        //then
        assertThat(isDeleted).isTrue();

        verify(redisTemplate).delete("key");
        verifyNoMoreInteractions(redisTemplate);
    }

    @Test
    void should_not_delete() {
        //given
        when(redisTemplate.delete("key")).thenReturn(false);

        //when
        Boolean isDeleted = redisService.delete("key");

        //then
        assertThat(isDeleted).isFalse();

        verify(redisTemplate).delete("key");
        verifyNoMoreInteractions(redisTemplate);
    }

    @Test
    void throw_business_exception_for_delete() {
        //given
        Exception exception = new BusinessException("exception");
        when(redisTemplate.delete("key")).thenThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> redisService.delete("key"));

        //then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Business exception occurred Business exception occurred exception  ");
    }
}