package cmc.bobpossible.search;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.search.suggest.SuggestBuilder;
import org.opensearch.search.suggest.SuggestBuilders;
import org.opensearch.search.suggest.SuggestionBuilder;

import java.io.IOException;

public class ElasticSearch {
    private RestHighLevelClient client;

    public ElasticSearch() {
        //Point to keystore with appropriate certificates for security.
//        System.setProperty("javax.net.ssl.trustStore", "/full/path/to/keystore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "password-to-keystore");

        //Establish credentials to use basic authentication.
        //Only for demo purposes. Don't specify your credentials in code.
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("bobplace", "Bobplace1!"));

        //Create a client.
        RestClientBuilder builder = RestClient.builder(new HttpHost("search-bobplace-domain-lk4pashmaek2mwqgy5hwyaud4m.ap-northeast-2.es.amazonaws.com", 443, "https"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        client = new RestHighLevelClient(builder);
    }

    public String search() throws IOException {

        SearchRequest searchRequest = new SearchRequest("stores");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "죠스떡볶이"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return searchResponse.toString();

//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.completionSuggestion("search-string-suggest").text("죠스");
//
//        SuggestBuilder suggestBuilder = new SuggestBuilder();
//        suggestBuilder.addSuggestion("search_string", termSuggestionBuilder);
//        searchSourceBuilder.suggest(suggestBuilder);
//
//        SearchRequest searchRequest = new SearchRequest("auto_complete");
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        return searchResponse.toString();
    }
}
