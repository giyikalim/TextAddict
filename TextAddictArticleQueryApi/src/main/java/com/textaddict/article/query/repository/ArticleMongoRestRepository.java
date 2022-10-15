package com.textaddict.article.query.repository;

import com.textaddict.article.query.model.ArticleDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "articles", path = "articles")
public interface ArticleMongoRestRepository extends MongoRepository<ArticleDocument, String> {
    List<ArticleDocument> findByHeader(@Param("header") String header);

    @RestResource(path = "headerStartsWith", rel = "headerStartsWith")
    public Page<ArticleDocument> findByHeaderStartsWith(@Param("header") String header, Pageable p);
}
