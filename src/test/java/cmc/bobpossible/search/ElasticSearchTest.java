package cmc.bobpossible.search;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ElasticSearchTest {

    @Test
    void search() throws IOException {
        ElasticSearch elasticSearch = new ElasticSearch();

        System.out.println(elasticSearch.suggest("죠스"));
    }
}