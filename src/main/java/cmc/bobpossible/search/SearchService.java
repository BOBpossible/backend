package cmc.bobpossible.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SearchService {

    private final ElasticSearch elasticSearch;

    public String getSuggestion(String keyword) throws IOException {

        return elasticSearch.suggest(keyword);
    }
}
