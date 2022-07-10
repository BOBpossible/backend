package cmc.bobpossible.push;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.mission.dto.GetHome;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/fcm")
@RequiredArgsConstructor
@RestController
public class FCMController {

    private final FirebaseTokenService firebaseTokenService;

    @ApiOperation("이번주 미션 조회(홈화면)")
    @PostMapping("/me")
    public BaseResponse<String> postFCMToken(@RequestBody String token) throws BaseException {

        firebaseTokenService.postFCMToken(token);

        return new BaseResponse<>("");
    }
}
