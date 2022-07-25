package cmc.bobpossible.search;

import lombok.RequiredArgsConstructor;
import org.opensearch.search.SearchHit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SearchService {

    private final ElasticSearch elasticSearch;

    public List<SearchSuggestion> getSuggestion(String keyword) throws IOException {

        SearchHit[] suggest = elasticSearch.suggest(keyword);

        List<SearchSuggestion> suggestionList = new ArrayList<>();

        for (SearchHit searchHit : suggest) {
            suggestionList.add(new SearchSuggestion(searchHit.getSourceAsMap().get("search_string").toString()));
        }

        return suggestionList;
    }
}
