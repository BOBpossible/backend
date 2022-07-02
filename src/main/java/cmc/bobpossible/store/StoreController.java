package cmc.bobpossible.store;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.store.dto.GetStoreImages;
import cmc.bobpossible.store.dto.GetStoreMapRes;
import cmc.bobpossible.store.dto.GetStoreRes;
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
    public BaseResponse<String> createStore(@Validated @RequestPart PostStoreReq postStoreReq, Errors errors) throws BaseException, IOException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        storeService.createStore(postStoreReq);

        return new BaseResponse<>("");
    }

    @ApiOperation("대표메뉴 사진 등록")
    @PostMapping("/representative-menu-images/{storeId}")
    public BaseResponse<String> postRepresentativeMenuImages(@RequestPart List<MultipartFile> representativeMenuImages, @PathVariable Long storeId) throws BaseException, IOException {

        storeService.postRepresentativeMenuImages(representativeMenuImages, storeId);

        return new BaseResponse<>("");
    }

    @ApiOperation("가게 사진 등록")
    @PostMapping("/store-images/{storeId}")
    public BaseResponse<String> postStoreImages(@RequestPart List<MultipartFile> storeImages, @PathVariable Long storeId) throws BaseException, IOException {

        storeService.postStoreImages(storeImages, storeId);

        return new BaseResponse<>("");
    }

    @ApiOperation("가게 지도 조회")
    @GetMapping("")
    public BaseResponse<List<GetStoreMapRes>> getStoreMap() throws BaseException {
        return new BaseResponse<>(storeService.getStoreMap());
    }

    @ApiOperation("가게 상세 정보 조회")
    @GetMapping("/{storeId}")
    public BaseResponse<GetStoreRes> getStore(@PathVariable long storeId) throws BaseException {
        return new BaseResponse<>(storeService.getStore(storeId));
    }

}
