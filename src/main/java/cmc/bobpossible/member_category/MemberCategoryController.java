package cmc.bobpossible.member_category;

import cmc.bobpossible.category.Category;
import cmc.bobpossible.category.dto.GetCategoriesRes;
import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping("/api/v1/member-categories")
@RequiredArgsConstructor
@RestController
public class MemberCategoryController {

    private final MemberCategoryService memberCategoryService;

    @ApiOperation("고객 선호 음식 추가")
    @PostMapping("")
    public BaseResponse<String> createMemberCategories(@RequestParam List<Long> favorites) throws BaseException {

            memberCategoryService.createMemberCategories(favorites);

        return new BaseResponse<>("");
    }

    @ApiOperation("고객이 선호하는 음식종류들 조회")
    @GetMapping("/me")
    public BaseResponse<List<GetCategoriesRes>> getUserCategories() throws BaseException {

        List<GetCategoriesRes> getCategoriesRes = memberCategoryService.getUserCategories();

        return new BaseResponse<>(getCategoriesRes);
    }
}
