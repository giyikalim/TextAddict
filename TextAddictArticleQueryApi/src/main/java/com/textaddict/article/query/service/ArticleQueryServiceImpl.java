package com.textaddict.article.query.service;

import com.textaddict.article.query.model.ArticleDocument;
import com.textaddict.article.query.repository.ArticleMongoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
public class ArticleQueryServiceImpl implements ArticleQueryService {
    @Autowired
    ArticleMongoRepository articleRepository;

    @Override
    public List<ArticleDocument> findArticlesByHeader(String header) {
        return articleRepository.findArticlesByHeader(header);
    }
}
