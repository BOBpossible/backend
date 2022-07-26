package cmc.bobpossible.search;

import cmc.bobpossible.category.Category;
import cmc.bobpossible.category.CategoryRepository;
import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.mission.MissionRepository;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreRepository;
import cmc.bobpossible.store.dto.GetStoreMapRes;
import lombok.RequiredArgsConstructor;
import org.opensearch.search.SearchHit;
import org.opensearch.search.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cmc.bobpossible.config.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SearchService {

    private final ElasticSearch elasticSearch;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public List<SearchSuggestion> getSuggestion(String keyword) throws IOException {

        SearchHits suggest = elasticSearch.suggest(keyword);

        float maxScore = suggest.getMaxScore();

        List<SearchSuggestion> suggestionList = new ArrayList<>();

        for (SearchHit searchHit : suggest.getHits()) {
            if (searchHit.getScore() > (maxScore / 2)) {
                suggestionList.add(new SearchSuggestion(searchHit.getSourceAsMap().get("search_string").toString()));
            }
        }

        return suggestionList;
    }

    public List<GetStoreMapRes> getSearch(String keyword) throws IOException, BaseException {

        SearchHits suggest = elasticSearch.suggest(keyword);
        float maxScore = suggest.getMaxScore();

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<GetStoreMapRes> suggestionList = new ArrayList<>();

        for (SearchHit searchHit : suggest.getHits()) {
            if (searchHit.getScore() > (maxScore / 2)) {
                Store store = storeRepository.findById(Long.valueOf(searchHit.getId()))
                        .orElseThrow(() -> new BaseException(INVALID_STORE_ID));
                suggestionList.add(new GetStoreMapRes(member, store));
            }

        }

        return suggestionList;
    }

    public List<GetStoreMapRes> getTagSearch(Long categoryId) throws BaseException {

        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(INVALID_CATEGORY_ID));

        List<Store> byCategory = storeRepository.findByCategory(category);

        return byCategory.stream()
                .map(s -> new GetStoreMapRes(member, s))
                .collect(Collectors.toList());
    }
}
