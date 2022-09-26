package com.textaddict.userapi.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodName, Response response) {
        switch (response.status()) {
            case 400:
                // do something
                break;
            case 404:
                if (methodName.contains("getArticles")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Articles api could not found!");
                }
                break;
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
