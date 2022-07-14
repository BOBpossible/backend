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

    @ApiOperation("fcm 성공 요청 수락 테스트 ")
    @PostMapping("/success/{userId}/{missionId}")
    public BaseResponse<String> test(@PathVariable long userId,@PathVariable long missionId) throws BaseException, IOException {

        firebaseTokenService.test(userId,missionId);

        return new BaseResponse<>("");
    }

    @ApiOperation("fcm 성공 요청 거절 테스트 ")
    @PostMapping("/fail/{userId}/{missionId}")
    public BaseResponse<String> test2(@PathVariable long userId,@PathVariable long missionId) throws BaseException, IOException {

        firebaseTokenService.test2(userId,missionId);

        return new BaseResponse<>("");
    }
}
