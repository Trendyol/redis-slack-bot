package com.trendyol.redisslackbot.common.exception;

import com.trendyol.redisslackbot.common.exception.dto.ErrorDTO;
import com.trendyol.redisslackbot.common.exception.dto.ErrorDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {

    private static final Locale TR = new Locale("tr");
    private static final String UNEXPECTED_ERROR = "Beklenmeyen bir hata oluştu";

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        var errorDetailDTO = getErrorDetailTO(INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR);

        var errorDTO = ErrorDTO.builder()
                .exception(ex.getClass().getCanonicalName())
                .errorDetail(List.of(errorDetailDTO))
                .build();
        log.error("Exception Caused By: {1}", ex);
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> handleGeneralBusinessException(BusinessException be) {
        var errorDetailDTO = getErrorDetailTO(BAD_REQUEST, be.getMessage());

        var errorDTO = ErrorDTO.builder()
                .exception(be.getClass().getCanonicalName())
                .errorDetail(List.of(errorDetailDTO))
                .build();
        log.error("GeneralBusinessException Caused By: {1}", be);
        return new ResponseEntity<>(errorDTO, BAD_REQUEST);
    }

    private String getMessage(String key, Object[] args, String defaultMessage) {
        return Optional.of(getMessage(() -> messageSource.getMessage(key, args, TR)))
                .filter(StringUtils::isNotBlank)
                .orElse(defaultMessage);
    }

    private String getMessage(Supplier<String> supplier) {
        String message = StringUtils.EMPTY;
        try {
            message = supplier.get();
        } catch (Exception exception) {
            log.warn("Exception occurred : ", exception);
        }
        return message;
    }

    private ErrorDetailDTO getErrorDetailTO(HttpStatus httpStatus, String message) {
        return ErrorDetailDTO.builder()
                .message(message)
                .errorCode(String.valueOf(httpStatus.value()))
                .build();
    }
}