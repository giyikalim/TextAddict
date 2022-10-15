package com.textaddict.article.query.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "together", types = { ArticleDocument.class })
public interface ArticleProjectionHeaderAndTextTogether {

    @Value("#{target.header} #{target.text}")
    String getFullName();

}