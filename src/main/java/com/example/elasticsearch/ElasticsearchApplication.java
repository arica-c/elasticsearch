package com.example.elasticsearch;

import com.example.elasticsearch.controllers.ElasticSearch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.elasticsearch.controllers")
public class ElasticsearchApplication {

    public static void main(String[] args) {
        ElasticSearch elasticSearch= new ElasticSearch();
        elasticSearch.createIndex();
        elasticSearch.addDocuments();
        SpringApplication.run(ElasticsearchApplication.class, args);

    }
}
