package com.textaddict.article.command.data;


import com.textaddict.dto.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="textaddict-user-api")
public interface UserApiClient {
    Logger logger= LoggerFactory.getLogger(UserApiClient.class);
    @GetMapping("/users/{id}")
    @Retry(name="getUserDetails")
    @CircuitBreaker(name = "getUserDetails", fallbackMethod = "getUserDetailsFallback")
    public UserDto getUserDetails(@PathVariable String id);

    default UserDto getUserDetailsFallback(String id, Throwable ex){
        logger.error(ex.getMessage(),ex);
        UserDto userDto=new UserDto();
        userDto.setUuid(id);
        return null;
    }
}

