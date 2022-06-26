package cmc.bobpossible.store;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.store.dto.GetStoreImages;
import cmc.bobpossible.store.dto.GetStoreMapRes;
import cmc.bobpossible.store.dto.GetStoreReviewRes;
import cmc.bobpossible.store.dto.PostStoreReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @ApiOperation("가게 정보 등록")
    @PostMapping("")
    public BaseResponse<String> createStore(@Validated @RequestBody PostStoreReq postStoreReq, @RequestPart List<MultipartFile> representativeMenuImages, Errors errors) throws BaseException, IOException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        storeService.createStore(postStoreReq, representativeMenuImages);

        return new BaseResponse<>("");
    }

    @ApiOperation("가게 지도 조회")
    @GetMapping("")
    public BaseResponse<List<GetStoreMapRes>> getStoreMap() throws BaseException {
        return new BaseResponse<>(storeService.getStoreMap());
    }

    @ApiOperation("가게 이미지들 조회")
    @GetMapping("images/{storeId}")
    public BaseResponse<Slice<GetStoreImages>> getStoreImages(Pageable pageable, @PathVariable Long storeId) throws BaseException {
        return new BaseResponse<>(storeService.getStoreImages(storeId, pageable));
    }

    @ApiOperation("가게 리뷰 조회")
    @GetMapping("reviews/{storeId}")
    public BaseResponse<Slice<GetStoreReviewRes>> getStoreReviewRes(Pageable pageable, @PathVariable Long storeId) throws BaseException {
        return new BaseResponse<>(storeService.getStoreReviewRes(storeId, pageable));
    }

}
