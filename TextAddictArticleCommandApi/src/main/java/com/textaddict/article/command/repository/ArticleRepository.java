package com.textaddict.article.command.repository;

import com.textaddict.article.command.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArticleRepository extends CrudRepository<Article, UUID> {
}
