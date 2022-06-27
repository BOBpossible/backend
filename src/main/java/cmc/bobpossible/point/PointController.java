package cmc.bobpossible.point;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.point.dto.GetMyPoints;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
@RestController
public class PointController {

    private final PointService pointService;

    @ApiOperation("나의 포인트 조회")
    @GetMapping("/me")
    public BaseResponse<GetMyPoints> getMyPoints(Pageable pageable) throws BaseException {

        return new BaseResponse<>(pointService.getMyPoints(pageable));
    }
}
