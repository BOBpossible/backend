package cmc.bobpossible.mission;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.mission.dto.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/api/v1/missions")
@RequiredArgsConstructor
@RestController
public class MissionController {

    private final MissionService missionService;

    @ApiOperation("이번주 미션 조회(홈화면)")
    @GetMapping("/me")
    public BaseResponse<GetHome> getMissions() throws BaseException {

        return new BaseResponse<>(missionService.getMissions());
    }

    @ApiOperation("나의 현재 진행 미션 조회")
    @GetMapping("/me/progress")
    public BaseResponse<List<GetMissionRes>> getMissionOnProgress() throws BaseException {

        List<Mission> missions = missionService.getMissionOnProgress();

        return new BaseResponse<>(
                missions.stream()
                        .map(GetMissionRes::new)
                        .collect(Collectors.toList()));
    }

    @ApiOperation("나의 완료 미션 조회")
    @GetMapping("/me/complete")
    public BaseResponse<List<GetMissionsCompleteRes>> getCompleteMission() throws BaseException {

        List<Mission> missions = missionService.getCompleteMission();

        // 3일 이내 리뷰 validation check
        missions.forEach(m -> {
            if (m.getMissionSuccessDate().plusDays(3).isBefore(LocalDateTime.now())) {
                m.reviewDone();
            }
        });

        return new BaseResponse<>(
                missions.stream()
                        .map(GetMissionsCompleteRes::new)
                        .collect(Collectors.toList()));
    }

    @ApiOperation("미션 성공 요청(고객)")
    @PatchMapping("/users/success-request/{missionId}")
    public BaseResponse<String> postRequestCompleteMission(@PathVariable Long missionId) throws BaseException, IOException {

        missionService.postRequestCompleteMission(missionId);

        return new BaseResponse<>("");
    }

    @ApiOperation("미션 취소")
    @PatchMapping("/users/cancel/{missionId}")
    public BaseResponse<String> cancelChallenge(@PathVariable Long missionId) throws BaseException {

        missionService.cancelChallenge(missionId);

        return new BaseResponse<>("");
    }

    @ApiOperation("미션 도전(고객)")
    @PatchMapping("/challenge/{missionId}")
    public BaseResponse<String> missionChallenge(@PathVariable Long missionId) throws BaseException, IOException {

        missionService.missionChallenge(missionId);

        return new BaseResponse<>("");
    }

    @ApiOperation("미션 성공")
    @PatchMapping("/success/{missionId}")
    public BaseResponse<String> missionSuccess(@PathVariable Long missionId) throws BaseException, IOException {

        missionService.missionSuccess(missionId);

        return new BaseResponse<>("");
    }


    //사장

    @ApiOperation("사장 진행중 미션 조회(사장)")
    @GetMapping("/owners/progress")
    public BaseResponse<GetOwnerMissionRes> getOwnersMissionOnProgress() throws BaseException {

        return new BaseResponse<>(missionService.getOwnersMissionOnProgress());
    }

    @ApiOperation("사장 성공요청 미션 조회(사장)")
    @GetMapping("/owners/success")
    public BaseResponse<List<OwnerSuccessMissionRes>> getOwnersMissionOnSuccess() throws BaseException {

        return new BaseResponse<>(missionService.getOwnersMissionOnSuccess());
    }

    @ApiOperation("미션 관리(사장)")
    @GetMapping("/owners/mission-manage")
    public BaseResponse<List<GetMissionManageRes>> getMissionManage() throws BaseException {

        return new BaseResponse<>(missionService.getMissionManage());
    }

    @ApiOperation("미션 관리 노출수(사장)")
    @GetMapping("/owners/mission-manage/count")
    public BaseResponse<GetMissionManageCountRes> getMissionManageCount() throws BaseException {

        return new BaseResponse<>(missionService.getMissionManageCount());
    }

    @ApiOperation("미션 배포 중지(사장)")
    @PatchMapping("/owners/mission-stop/{missionGroupId}")
    public BaseResponse<String> stopMissionGroup(@PathVariable long missionGroupId) throws BaseException {

        missionService.stopMissionGroup(missionGroupId);

        return new BaseResponse<>("");
    }

    @ApiOperation("미션 전체 배포 중지(사장)")
    @PatchMapping("/owners/mission-stop")
    public BaseResponse<String> stopAllMissionGroup() throws BaseException {

        missionService.stopAllMissionGroup();

        return new BaseResponse<>("");
    }

    @ApiOperation("미션 배포 활성화(사장)")
    @PatchMapping("/owners/mission-active/{missionGroupId}")
    public BaseResponse<String> activeMissionGroup(@PathVariable long missionGroupId) throws BaseException {

        missionService.activeMissionGroup(missionGroupId);

        return new BaseResponse<>("");
    }

    @ApiOperation("결제 취소(사장)")
    @PostMapping("/owners/{missionId}")
    public BaseResponse<String> cancelMission(@PathVariable long missionId, @RequestParam String reason) throws BaseException, IOException {

        missionService.cancelMission(missionId, reason);

        return new BaseResponse<>("");
    }

    @ApiOperation("미션 그룹 상세정보(사장)")
    @GetMapping("/owners/mission-manage/{missionGroupId}")
    public BaseResponse<List<GetMissionGroupRes>> GetMissionGroupDetail(Pageable pageable, @PathVariable long missionGroupId) throws BaseException {



        return new BaseResponse<>(missionService.GetMissionGroupDetail(pageable, missionGroupId));
    }

    @ApiOperation("미션 수락(사장)")
    @PatchMapping("/accept/{missionId}")
    public BaseResponse<String> acceptMission(@PathVariable Long missionId) throws BaseException, IOException {

        missionService.acceptMission(missionId);

        return new BaseResponse<>("");
    }

    @ApiOperation("미션 거절(사장)")
    @PatchMapping("/deny/{missionId}")
    public BaseResponse<String> denyMission(@PathVariable Long missionId) throws BaseException, IOException {

        missionService.denyMission(missionId);

        return new BaseResponse<>("");
    }
}
