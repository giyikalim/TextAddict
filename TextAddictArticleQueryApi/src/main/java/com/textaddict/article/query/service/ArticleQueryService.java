package com.textaddict.article.query.service;

import com.textaddict.article.query.model.ArticleDocument;

import java.util.List;

public interface ArticleQueryService {
    List<ArticleDocument> findArticlesByHeader(String header);
}
