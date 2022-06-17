package cmc.bobpossible.favorite;

import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.favorite.dto.GetCategoriesRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.*;

@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("선호하는 음식종류들 조회")
    @GetMapping("/")
    public BaseResponse<List<GetCategoriesRes>> getCategories() {

        List<Category> categories = categoryService.getCategories();

        List<GetCategoriesRes> getCategoriesRes = categories.stream()
                .map(GetCategoriesRes::new)
                .collect(toList());

        return new BaseResponse<>(getCategoriesRes);
    }

}
