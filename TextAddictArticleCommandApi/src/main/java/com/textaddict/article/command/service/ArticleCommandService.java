package com.textaddict.article.command.service;

import com.textaddict.article.command.model.Article;
import com.textaddict.article.command.model.ArticlePage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleCommandService {
    Article saveArticle(Article article);

    ArticlePage createArticlePage(ArticlePage articlePage);

    Optional<Article> findById(UUID uuid);

    Optional<ArticlePage> findArticlePageById(UUID uuid);

    List<Article> findArticlesByCreatedBy(String createdBy);
}
