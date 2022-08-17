package cmc.bobpossible.push;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.push.dto.GetPushRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/push-notifications")
@RequiredArgsConstructor
@RestController
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @ApiOperation("알림 내역")
    @GetMapping("/me")
    public BaseResponse<List<GetPushRes>> getPushNotifications() throws BaseException {

        return new BaseResponse<>( pushNotificationService.getPushNotifications());
    }

    @ApiOperation("알림 읽음")
    @PatchMapping("/me/{pushNotificationId}")
    public BaseResponse<String> checkPush(@PathVariable Long pushNotificationId) throws BaseException {

        pushNotificationService.checkPush(pushNotificationId);

        return new BaseResponse<>("");
    }

    @ApiOperation("알림 전체 읽음")
    @PatchMapping("/me/all")
    public BaseResponse<String> checkAllPush() throws BaseException {

        pushNotificationService.checkAllPush();

        return new BaseResponse<>("");
    }
}
