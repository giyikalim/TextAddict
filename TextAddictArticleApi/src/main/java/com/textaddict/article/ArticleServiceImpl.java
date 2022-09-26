package com.textaddict.article;

import com.textaddict.article.model.Article;
import com.textaddict.article.repository.ArticleRepository;
import com.textaddict.article.service.ArticleService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }
}
