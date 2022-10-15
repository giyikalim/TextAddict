package com.textaddict.article.command.repository;

import com.textaddict.article.command.model.Article;
import com.textaddict.article.command.model.ArticlePage;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArticlePageRepository extends CrudRepository<ArticlePage, UUID> {
}
