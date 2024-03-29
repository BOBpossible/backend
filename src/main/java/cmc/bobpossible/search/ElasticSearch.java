package cmc.bobpossible.search;

import cmc.bobpossible.store.Store;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.action.delete.DeleteResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHits;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
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

    public SearchHits suggest(String text) throws IOException {

        SearchRequest searchRequest = new SearchRequest("auto_complete");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("search_string.ngram", text));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return searchResponse.getHits();
    }

    public String search() throws IOException {

        SearchRequest searchRequest = new SearchRequest("auto_complete");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("search_string.ngram", "볶"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return searchResponse.toString();

    }

    public void add(Store store) throws IOException {
        IndexRequest request = new IndexRequest("auto_complete"); //Add a document to the custom-index we created.
        request.id(String.valueOf(store.getId())); //Assign an ID to the document.

        HashMap<String, String> stringMapping = new HashMap<String, String>();
        stringMapping.put("search_string", store.getName());
        stringMapping.put("search_string2", store.getCategory().getName());
        request.source(stringMapping); //Place your content into the index's source.
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

    }


    public void delete(Long id) throws IOException {

        //Delete the document
        DeleteRequest deleteDocumentRequest = new DeleteRequest("auto_complete", String.valueOf(id)); //Index name followed by the ID.
        DeleteResponse deleteResponse = client.delete(deleteDocumentRequest, RequestOptions.DEFAULT);


    }
}
