package com.textaddict.article.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "noText", types = { Article.class })
interface noText {
    String getHeader();
}
