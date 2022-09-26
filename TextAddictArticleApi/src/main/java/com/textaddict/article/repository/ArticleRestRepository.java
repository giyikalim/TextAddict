package com.textaddict.article.repository;

import com.textaddict.article.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "articles", path = "articles")
public interface ArticleRestRepository extends PagingAndSortingRepository<Article, UUID> {
    List<Article> findByHeader(@Param("header") String header);

    @RestResource(path = "headerStartsWith", rel = "headerStartsWith")
    public Page findByHeaderStartsWith(@Param("header") String header, Pageable p);
}
