package com.textaddict.article.repository;

import com.textaddict.article.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArticleRepository extends CrudRepository<Article, UUID> {
}
