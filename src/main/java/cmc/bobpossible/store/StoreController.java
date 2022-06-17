package cmc.bobpossible.store;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.member.dto.PostOwnerReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

//    @ApiOperation("가게 정보 등록")
//    @PostMapping("/")
//    public BaseResponse<String> joinOwner(@Validated @RequestBody PostOwnerReq postOwnerReq, Errors errors) throws BaseException {
//
//        //validation
//        if (errors.hasErrors()) {
//            return new BaseResponse<>(RefineError.refine(errors));
//        }
//
//
//        return new BaseResponse<>("");
//    }
}
