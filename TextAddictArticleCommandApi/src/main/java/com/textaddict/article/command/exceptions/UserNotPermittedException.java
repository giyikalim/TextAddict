package com.textaddict.article.command.exceptions;

public class UserNotPermittedException extends RuntimeException {
    public UserNotPermittedException(String message) {
        super(message);
    }
}
