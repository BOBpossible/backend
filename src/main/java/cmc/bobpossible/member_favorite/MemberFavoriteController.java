package cmc.bobpossible.member_favorite;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/member-favorites")
@RequiredArgsConstructor
@RestController
public class MemberFavoriteController {

    private final MemberFavoriteService memberFavoriteService;

    @ApiOperation("고객 선호 음식 추가")
    @PostMapping("/")
    public BaseResponse<String> createMemberFavorites(@RequestParam List<Long> favorites) throws BaseException {

        for (Long favorite : favorites) {
            memberFavoriteService.createMemberFavorites(favorite);
        }

        return new BaseResponse<>("");
    }
}
