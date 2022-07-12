package cmc.bobpossible.point_conversion;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.point_conversion.dto.CreatePointConversion;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/api/v1/points-conversion")
@RequiredArgsConstructor
@RestController
public class PointConversionController {

    private final PointConversionService pointConversionService;

    @ApiOperation("포인트 전환")
    @PostMapping("/me")
    public BaseResponse<String> createPointConversion(@Validated @RequestBody CreatePointConversion createPointConversion, Errors errors) throws BaseException, IOException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        pointConversionService.createPointConversion(createPointConversion);

        return new BaseResponse<>("");
    }
}
