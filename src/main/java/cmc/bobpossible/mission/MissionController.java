package cmc.bobpossible.mission;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.mission.dto.GetMissionsRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/api/v1/missions")
@RequiredArgsConstructor
@RestController
public class MissionController {

    private final MissionService missionService;

    @ApiOperation("나의 현재 미션 조회")
    @GetMapping("/me")
    public BaseResponse<List<GetMissionsRes>> getMissions() throws BaseException {

        List<Mission> missions = missionService.getMissions();

        return new BaseResponse<>(
                missions.stream()
                        .map(GetMissionsRes::new)
                        .collect(Collectors.toList()));
    }

    @ApiOperation("나의 현재 진행 미션 조회")
    @GetMapping("/me/progress")
    public BaseResponse<List<GetMissionsRes>> getMissionOnProgress() throws BaseException {

        List<Mission> missions = missionService.getMissionOnProgress();

        return new BaseResponse<>(
                missions.stream()
                        .map(GetMissionsRes::new)
                        .collect(Collectors.toList()));
    }

    @ApiOperation("나의 완료 미션 조회")
    @GetMapping("/me/complete")
    public BaseResponse<List<GetMissionsRes>> getCompleteMission() throws BaseException {

        List<Mission> missions = missionService.getCompleteMission();

        return new BaseResponse<>(
                missions.stream()
                        .map(GetMissionsRes::new)
                        .collect(Collectors.toList()));
    }

    @ApiOperation("미션 성공 요청(고객)")
    @PatchMapping("/users/mission-status/{missionId}")
    public BaseResponse<String> postRequestCompleteMission(@PathVariable Long missionId) throws BaseException {

        missionService.postRequestCompleteMission(missionId);

        return new BaseResponse<>("");
    }


}
