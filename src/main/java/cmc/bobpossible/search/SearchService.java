package cmc.bobpossible.search;

import cmc.bobpossible.config.BaseException;
import cmc.bobpossible.config.auth.SecurityUtil;
import cmc.bobpossible.member.MemberRepository;
import cmc.bobpossible.member.entity.Member;
import cmc.bobpossible.store.Store;
import cmc.bobpossible.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.opensearch.search.SearchHit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cmc.bobpossible.config.BaseResponseStatus.CHECK_QUIT_USER;
import static cmc.bobpossible.config.BaseResponseStatus.INVALID_STORE_ID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SearchService {

    private final ElasticSearch elasticSearch;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public List<SearchSuggestion> getSuggestion(String keyword) throws IOException {

        SearchHit[] suggest = elasticSearch.suggest(keyword);

        List<SearchSuggestion> suggestionList = new ArrayList<>();

        for (SearchHit searchHit : suggest) {
            suggestionList.add(new SearchSuggestion(searchHit.getSourceAsMap().get("search_string").toString()));
        }

        return suggestionList;
    }

    public List<SearchRes> getSearch(String keyword) throws IOException, BaseException {
        SearchHit[] suggest = elasticSearch.suggest(keyword);
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BaseException(CHECK_QUIT_USER));

        List<SearchRes> suggestionList = new ArrayList<>();

        for (SearchHit searchHit : suggest) {
            Store store = storeRepository.findById(Long.valueOf(searchHit.getId()))
                    .orElseThrow(() -> new BaseException(INVALID_STORE_ID));
            suggestionList.add(new SearchRes(member, store));
        }

        return suggestionList;
    }
}
