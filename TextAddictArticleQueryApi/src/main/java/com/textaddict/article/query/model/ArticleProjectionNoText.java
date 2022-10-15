package com.textaddict.article.query.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "noText", types = { ArticleDocument.class })
interface noText {
    String getHeader();
}
