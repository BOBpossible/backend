package cmc.bobpossible.push.firebase;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequestMapping("/api/v1/fcm")
@RequiredArgsConstructor
@RestController
public class FCMController {

    private final FirebaseTokenService firebaseTokenService;

    @ApiOperation("fcm 토큰 등록")
    @PostMapping("/me")
    public BaseResponse<String> postFCMToken(@RequestBody FcmToken token) throws BaseException {

        firebaseTokenService.postFCMToken(token);

        return new BaseResponse<>("");
    }

    @ApiOperation("fcm test")
    @PostMapping("/test")
    public BaseResponse<String> test() throws BaseException, IOException {

        firebaseTokenService.test();

        return new BaseResponse<>("");
    }
}
