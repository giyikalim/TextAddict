package com.textaddict.userapi.data;


import com.textaddict.userapi.ui.model.response.ArticleResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="textaddict-article-query-api")
public interface ArticleApiClient {
    @GetMapping("/articles/users/{id}")
    @Retry(name="getArticles")
    @CircuitBreaker(name = "getArticles", fallbackMethod = "getArticlesFallback")
    public List<ArticleResponse> getArticles(@PathVariable String id);

    default List<ArticleResponse> getArticlesFallback(String id, Throwable ex){
        System.out.println(id);
        System.out.println(ex.getMessage());
        return new ArrayList<>();
    }
}

