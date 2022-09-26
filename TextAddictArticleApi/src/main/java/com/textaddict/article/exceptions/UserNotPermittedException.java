package com.textaddict.article.exceptions;

public class UserNotPermittedException extends RuntimeException {
    public UserNotPermittedException(String message) {
        super(message);
    }
}
