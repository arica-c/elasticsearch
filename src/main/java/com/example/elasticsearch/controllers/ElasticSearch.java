package com.example.elasticsearch.controllers;

import com.example.elasticsearch.models.Category;
import com.example.elasticsearch.services.MysqlConnection;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Connects to ES
@RestController
public class ElasticSearch {
    public void createIndex() {
        try {
            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost("localhost", 9200, "http")));
            CreateIndexRequest request = new CreateIndexRequest("category");
            request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 0));
            Map<String, Object> cat_id = new HashMap<>();
            cat_id.put("type", "integer");
            Map<String, Object> cat_title = new HashMap<>();
            cat_title.put("type", "text");
            cat_title.put("analyzer","simple");
            Map<String, Object> cat_pages = new HashMap<>();
            cat_pages.put("type", "integer");
            Map<String, Object> cat_subcats = new HashMap<>();
            cat_subcats.put("type", "integer");
            Map<String, Object> cat_files= new HashMap<>();
            cat_files.put("type", "integer");
            Map<String, Object> tags= new HashMap<>();
            tags.put("type","keyword");
            Map<String, Object> properties = new HashMap<>();
            properties.put("cat_id", cat_id);
            properties.put("cat_title", cat_title);
            properties.put("tags", tags);
            Map<String, Object> mapping = new HashMap<>();
            mapping.put("properties", properties);
            request.mapping(mapping);
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);


            System.out.println("response id: " + createIndexResponse.index());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addDocuments() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        //get the arraylist
        MysqlConnection mysqlConnection = new MysqlConnection();
        List<Category> arraylist = new ArrayList<>();
        System.out.println(arraylist.size());
        //add Documents to ES
        try {
            BulkRequest bulkRequest = new BulkRequest();
            Category c = new Category();
            arraylist= mysqlConnection.sqlConnect("select * from category limit 500000");
            System.out.println(arraylist.size());
            for (int i = 0; i < 500000; i++) {
                c = arraylist.get(i);
                bulkRequest.add(new IndexRequest("category").id(Integer.toString(c.getCategory_Id()))
                        .source(XContentType.JSON, "cat_title", c.getCategory_title(), "cat_pages", c.getCategory_pages(), "cat_subcats", c.getCategory_subcats(), "cat_files", c.getCategory_files(), "tags", c.getTags()));
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT);


            bulkRequest = new BulkRequest();
            arraylist= mysqlConnection.sqlConnect("select * from category limit 500000,500000");
            for (int i = 0; i < 500000; i++) {
                c = arraylist.get(i);
                bulkRequest.add(new IndexRequest("category").id(Integer.toString(c.getCategory_Id()))
                        .source(XContentType.JSON, "cat_title", c.getCategory_title(), "cat_pages", c.getCategory_pages(), "cat_subcats", c.getCategory_subcats(), "cat_files", c.getCategory_files(), "tags", c.getTags()));
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT);


            bulkRequest = new BulkRequest();
            arraylist= mysqlConnection.sqlConnect("select * from category limit 1000000,500000");
            for (int i = 0; i < 500000; i++) {
                c = arraylist.get(i);
                bulkRequest.add(new IndexRequest("category").id(Integer.toString(c.getCategory_Id()))
                        .source(XContentType.JSON, "cat_title", c.getCategory_title(), "cat_pages", c.getCategory_pages(), "cat_subcats", c.getCategory_subcats(), "cat_files", c.getCategory_files(), "tags", c.getTags()));
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT);

            bulkRequest = new BulkRequest();
            arraylist= mysqlConnection.sqlConnect("select * from category limit 1500000,500000");
            for (int i = 0; i < 500000; i++) {
                c = arraylist.get(i);
                bulkRequest.add(new IndexRequest("category").id(Integer.toString(c.getCategory_Id()))
                        .source(XContentType.JSON, "cat_title", c.getCategory_title(), "cat_pages", c.getCategory_pages(), "cat_subcats", c.getCategory_subcats(), "cat_files", c.getCategory_files(), "tags", c.getTags()));
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT);

            bulkRequest = new BulkRequest();
            arraylist= mysqlConnection.sqlConnect("select * from category limit 2000000,500000");
            for (int i = 0; i < 500000; i++) {
                c = arraylist.get(i);
                bulkRequest.add(new IndexRequest("category").id(Integer.toString(c.getCategory_Id()))
                        .source(XContentType.JSON, "cat_title", c.getCategory_title(), "cat_pages", c.getCategory_pages(), "cat_subcats", c.getCategory_subcats(), "cat_files", c.getCategory_files(), "tags", c.getTags()));
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @GetMapping(value= "/match/{field}/{searchValue}")
    public String match(@PathVariable String field, @PathVariable String searchValue) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        try {
            SearchRequest searchRequest = new SearchRequest("category");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            MatchQueryBuilder matchQueryBuilder = new
                    MatchQueryBuilder(field, searchValue);
            searchSourceBuilder.query(matchQueryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.toString();
        }
        catch (Exception e){
          return  e.toString();
        }
    }


    @GetMapping(value= "/prefix/{field}/{prefix}")
    public String prefixMatch(@PathVariable String field, @PathVariable String prefix){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        try{
            SearchRequest searchRequest = new SearchRequest("category");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            PrefixQueryBuilder prefixQueryBuilder= new PrefixQueryBuilder(field, prefix);
            searchSourceBuilder.query(prefixQueryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.toString();
        }
        catch(Exception e){
            return  e.toString();
        }
    }

    @GetMapping(value= "/term/{field}/{searchValue}")
    public String termMatch(@PathVariable String field, @PathVariable String searchValue){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        try{
            SearchRequest searchRequest = new SearchRequest("category");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            TermQueryBuilder termQueryBuilder= new TermQueryBuilder(field, searchValue);
            searchSourceBuilder.query(termQueryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.toString();
        }
        catch(Exception e){
            return  e.toString();
        }
    }

    @GetMapping(value="/analyze/{input}")
    public String analyze(@PathVariable("input") String input){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        try {
            Map<String, Object> stopFilter = new HashMap<>();
            stopFilter.put("type", "stop");
            stopFilter.put("stopwords", new String[]{"to"});
            AnalyzeRequest request = AnalyzeRequest.buildCustomAnalyzer("standard")
                    .addTokenFilter("lowercase")
                    .addTokenFilter(stopFilter)
                    .build(input);

            AnalyzeResponse response = client.indices().analyze(request, RequestOptions.DEFAULT);
            List<AnalyzeResponse.AnalyzeToken> list= response.getTokens();
            StringBuilder stringBuffer= new StringBuilder(new StringBuffer());
            for(AnalyzeResponse.AnalyzeToken a: list) {
                stringBuffer.append(a.getTerm()+ "  ");
            }
            return stringBuffer.toString();
        }
        catch (Exception e){
            return e.toString();
        }

    }

}
