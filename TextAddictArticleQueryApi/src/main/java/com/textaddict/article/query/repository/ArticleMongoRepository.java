package com.textaddict.article.query.repository;

import com.textaddict.article.query.model.ArticleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ArticleMongoRepository extends MongoRepository<ArticleDocument, String> {
    @Query(value = "{'header': {'$regex': ?0, $options:'i'}}")
    //@Query(value = "{$or:[{name:{$regex:?0,$options:'i'}},{email:{$regex:?0,$options:'i'}}]}")
    List<ArticleDocument> findArticlesByHeader(String header);

    long count();
}
