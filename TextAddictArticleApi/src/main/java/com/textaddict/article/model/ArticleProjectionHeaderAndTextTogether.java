package com.textaddict.article.model;

import com.textaddict.article.model.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "together", types = { Article.class })
public interface ArticleProjectionHeaderAndTextTogether {

    @Value("#{target.header} #{target.text}")
    String getFullName();

}