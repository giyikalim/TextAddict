package com.textaddict.article.query.service;

import com.textaddict.dto.ArticleDto;

public interface ArticleServiceEventHandler {
    void createArticle(ArticleDto article);
}