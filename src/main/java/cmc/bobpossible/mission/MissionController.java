package cmc.bobpossible.mission;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.mission.dto.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

        log.info("들어옴");
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

        return new BaseResponse<>(
                missions.stream()
                        .map(GetMissionsCompleteRes::new)
                        .collect(Collectors.toList()));
    }

    @ApiOperation("미션 성공 요청(고객)")
    @PatchMapping("/users/success/{missionId}")
    public BaseResponse<String> postRequestCompleteMission(@PathVariable Long missionId) throws BaseException {

        missionService.postRequestCompleteMission(missionId);

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
    public BaseResponse<List<OwnerMissionDto>> getOwnersMissionOnSuccess() throws BaseException {

        return new BaseResponse<>(missionService.getOwnersMissionOnSuccess());
    }
}
