package cmc.bobpossible.config;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static cmc.bobpossible.config.BaseResponseStatus.IMAGE_UPLOAD_FAIL;
import static cmc.bobpossible.config.BaseResponseStatus.IO_EXCEPTION;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(BaseException.class)
    private BaseResponse<String> handleException(BaseException e) {
        return new BaseResponse<>(e.getStatus());
    }

    @ExceptionHandler(IOException.class)
    private BaseResponse<String> handleException(IOException e) {
        return new BaseResponse<>("IOException"+e.getMessage());
    }

    @ExceptionHandler(CoolsmsException.class)
    private BaseResponse<String> handleException(CoolsmsException e) {
        return new BaseResponse<>("SmsException"+e.getMessage());
    }
}
