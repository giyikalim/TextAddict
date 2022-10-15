package com.textaddict.consumer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ArticleMongoRepository extends MongoRepository<ArticleDocument, String> {

}