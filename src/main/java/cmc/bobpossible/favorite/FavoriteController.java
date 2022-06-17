package cmc.bobpossible.favorite;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.favorite.dto.GetFavoritesRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    @ApiOperation("선호하는 음식종류들 조회")
    @GetMapping("/")
    public BaseResponse<List<GetFavoritesRes>> getFavorites() {

        List<Favorite> favorites = favoriteService.getFavorites();

        List<GetFavoritesRes> getFavoritesRes = favorites.stream()
                .map(GetFavoritesRes::new)
                .collect(toList());

        return new BaseResponse<>(getFavoritesRes);
    }

}
