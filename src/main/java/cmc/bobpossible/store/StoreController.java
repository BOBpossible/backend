package cmc.bobpossible.store;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.review.dto.ImageDto;
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
    public BaseResponse<Long> createStore(@Validated @RequestBody PostStoreReq postStoreReq, Errors errors) throws BaseException, IOException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }


        return new BaseResponse<>(storeService.createStore(postStoreReq));
    }

    @ApiOperation("가게 id 조회")
    @GetMapping("/me/id")
    public BaseResponse<Long> getStoreId() throws BaseException {
        return new BaseResponse<>(storeService.getStoreId());
    }

    @ApiOperation("대표메뉴 사진 등록")
    @PostMapping("/representative-menu-images/{storeId}")
    public BaseResponse<List<Long>> postRepresentativeMenuImages(@RequestPart List<MultipartFile> representativeMenuImages, @PathVariable Long storeId) throws BaseException, IOException {


        return new BaseResponse<>(storeService.postRepresentativeMenuImages(representativeMenuImages, storeId));
    }

    @ApiOperation("대표메뉴 사진 삭제")
    @PatchMapping("/representative-menu-images/{menuImageId}")
    public BaseResponse<String> deleteMenuImage(@PathVariable Long menuImageId) throws BaseException {

        storeService.deleteMenuImage(menuImageId);

        return new BaseResponse<>("");
    }

    @ApiOperation("가게 사진 등록")
    @PostMapping("/store-images/{storeId}")
    public BaseResponse<List<Long>> postStoreImages(@RequestPart List<MultipartFile> storeImages, @PathVariable Long storeId) throws BaseException, IOException {


        return new BaseResponse<>(storeService.postStoreImages(storeImages, storeId));
    }

    @ApiOperation("가게 사진 삭제")
    @PatchMapping("/store-images/{storeImageId}")
    public BaseResponse<String> deleteStoreImage(@PathVariable Long storeImageId) throws BaseException, IOException {

        storeService.deleteStoreImage(storeImageId);

        return new BaseResponse<>("");
    }

    @ApiOperation("점포 인증 등록")
    @PostMapping("/store-authentication-images")
    public BaseResponse<String> postStoreAuthenticationImages(@RequestPart List<MultipartFile> storeAuthenticationImages) throws BaseException, IOException {

        storeService.postStoreAuthenticationImages(storeAuthenticationImages);

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

    @ApiOperation("점포관리 수정")
    @PutMapping("/operation-time/{operationTimeId}")
    public BaseResponse<String> updateOperationTime(@Validated OperationTimeVO operationTime, Errors errors, @PathVariable Long operationTimeId) throws BaseException {

        //validation
        if (errors.hasErrors()) {
            return new BaseResponse<>(RefineError.refine(errors));
        }

        storeService.updateOperationTime(operationTime, operationTimeId);

        return new BaseResponse<>("");
    }


    @ApiOperation("가게 상세 정보 조회(고객)")
    @GetMapping("/{storeId}")
    public BaseResponse<GetStoreRes> getStore(@PathVariable long storeId) throws BaseException {
        return new BaseResponse<>(storeService.getStore(storeId));
    }

    @ApiOperation("점포 관리 조회(사장)")
    @GetMapping("/me")
    public BaseResponse<GetOwnerStoreRes> getOwnerStore() throws BaseException {
        return new BaseResponse<>(storeService.getOwnerStore());
    }

    @ApiOperation("가게 상세 정보 조회 - 대표메뉴 조회")
    @GetMapping("/me/menu-images")
    public BaseResponse<List<ImageDto>> getMenuImages() throws BaseException {
        return new BaseResponse<>(storeService.getMenuImages());
    }

    @ApiOperation("가게 상세 - 가게 사진조회")
    @GetMapping("/store-images")
    public BaseResponse<List<ImageDto>> getStoreImages() throws BaseException {
        return new BaseResponse<>(storeService.getStoreImages());
    }

}
