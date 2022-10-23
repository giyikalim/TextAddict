package com.textaddict.consumer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleMongoRepository extends MongoRepository<ArticleDocument, String> {
    public Optional<ArticleDocument> findByUuid(String uuid);
}