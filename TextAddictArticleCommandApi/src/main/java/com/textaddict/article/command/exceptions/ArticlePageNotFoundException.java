package com.textaddict.article.command.exceptions;

public class ArticlePageNotFoundException extends RuntimeException{
    public ArticlePageNotFoundException(String message) {
        super(message);
    }
}
