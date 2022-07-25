package cmc.bobpossible.search;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.BaseResponse;
import cmc.bobpossible.config.RefineError;
import cmc.bobpossible.review.dto.PostReviewReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
}
