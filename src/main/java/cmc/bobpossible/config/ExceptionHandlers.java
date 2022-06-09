package cmc.bobpossible.config;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(BaseException.class)
    private BaseResponse<String> handleException(BaseException e) {
        return new BaseResponse<>(e.getStatus());
    }
}
