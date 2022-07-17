package cmc.bobpossible.member_category;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
