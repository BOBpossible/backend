package cmc.bobpossible.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESAutoCompleteRepository extends ElasticsearchRepository<ESAutoComplete, Integer> {
}
