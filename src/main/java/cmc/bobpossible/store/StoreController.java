package cmc.bobpossible.store;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.store.dto.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public BaseResponse<String> createStore(@Validated PostStoreReq postStoreReq, Errors errors) throws BaseException, IOException {

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

    @ApiOperation("대표메뉴 사진 삭제")
    @PatchMapping("/representative-menu-images/{menuImageId}")
    public BaseResponse<String> deleteMenuImage(@PathVariable Long menuImageId) throws BaseException {

        storeService.deleteMenuImage(menuImageId);

        return new BaseResponse<>("");
    }

    @ApiOperation("가게 사진 등록")
    @PostMapping("/store-images/{storeId}")
    public BaseResponse<String> postStoreImages(@RequestPart List<MultipartFile> storeImages, @PathVariable Long storeId) throws BaseException, IOException {

        storeService.postStoreImages(storeImages, storeId);

        return new BaseResponse<>("");
    }

    @ApiOperation("가게 사진 삭제")
    @PatchMapping("/store-images/{storeImageId}")
    public BaseResponse<String> deleteStoreImage(@PathVariable Long storeImageId) throws BaseException, IOException {

        storeService.deleteStoreImage(storeImageId);

        return new BaseResponse<>("");
    }

    @ApiOperation("점포관리 수정")
    @PutMapping("/me")
    public BaseResponse<String> updateStore(@Validated UpdateStoreReq updateStoreReq, Errors errors) throws BaseException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        storeService.updateStore(updateStoreReq);

        return new BaseResponse<>("");
    }


    @ApiOperation("가게 상세 정보 조회")
    @GetMapping("/{storeId}")
    public BaseResponse<GetStoreRes> getStore(@PathVariable long storeId) throws BaseException {
        return new BaseResponse<>(storeService.getStore(storeId));
    }

}
