package com.cheems.behind.exception;

import com.cheems.behind.commons.BaseResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GloablExceptionHandler {


    @ExceptionHandler(UserException.class)
    public BaseResponseResult handleUserException(UserException e) {
        return new BaseResponseResult(e.getCode(),e.getMessage(),e.getDescription());
    }

}
