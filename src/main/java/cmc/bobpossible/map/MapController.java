package cmc.bobpossible.map;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.mission.MissionService;
import cmc.bobpossible.mission.dto.GetMissionMapRes;
import cmc.bobpossible.store.StoreService;
import cmc.bobpossible.store.dto.GetStoreMapRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://loaclhost:8080")
@RequestMapping("/api/v1/map")
@RequiredArgsConstructor
@RestController
public class MapController {

    StoreService storeService;
    MissionService missionService;

    @ApiOperation("가게 지도 조회")
    @GetMapping("/stores/{userId}")
    public BaseResponse<List<GetStoreMapRes>> getStoreMap(@PathVariable Long userId) throws BaseException {
        return new BaseResponse<>(storeService.getStoreMap(userId));
    }

    @ApiOperation("미션 맵(홈화면)")
    @GetMapping("/mission/{missionId}")
    public BaseResponse<GetMissionMapRes> getMissionsMap(@PathVariable Long missionId) throws BaseException {

        return new BaseResponse<>(missionService.getMissionsMap(missionId));
    }
}
