package com.textaddict.article.query.exceptions;

public class UserNotPermittedException extends RuntimeException {
    public UserNotPermittedException(String message) {
        super(message);
    }
}
