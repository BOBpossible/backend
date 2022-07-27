package cmc.bobpossible.search;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.review.dto.PostReviewReq;
import cmc.bobpossible.store.dto.GetStoreMapRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3005", "https://bobplace.netlify.app"})
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    @ApiOperation("자동완성")
    @PostMapping("/suggestion")
    public BaseResponse<List<SearchSuggestion>> getSuggestion(@RequestParam String keyword) throws IOException {


        return new BaseResponse<>(searchService.getSuggestion(keyword));
    }

    @ApiOperation("검색결과")
    @PostMapping("/{userId}")
    public BaseResponse<List<GetStoreMapRes>> getSearch(@RequestParam String keyword, @PathVariable Long userId) throws IOException, BaseException {

        return new BaseResponse<>(searchService.getSearch(keyword, userId));
    }

    @ApiOperation("태그 검색결과")
    @PostMapping("/tag/{userId}/{categoryId}")
    public BaseResponse<List<GetStoreMapRes>> getTagSearch(@PathVariable Long categoryId, @PathVariable Long userId) throws IOException, BaseException {

        return new BaseResponse<>(searchService.getTagSearch(userId ,categoryId));
    }
}
