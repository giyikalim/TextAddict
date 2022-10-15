package com.textaddict.article.command.exceptions;

import com.textaddict.article.command.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {

        String message = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();
        ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date()).message(message).build();
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        String message = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();
        ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date()).message(message).build();
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UserNotPermittedException.class})
    public ResponseEntity<Object> handleAnyException(UserNotPermittedException ex, WebRequest request) {

        String message = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();
        ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date()).message(message).build();
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
