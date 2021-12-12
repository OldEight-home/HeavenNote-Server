package com.oldeighthome.heavennote.common.handler;

import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.common.exception.CustomSqlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomSqlException.class)
    public ApiResult handler(CustomSqlException e){
        String error=e.getMessage();
        log.error(error);
        return ApiResult.error(error);
    }
}
