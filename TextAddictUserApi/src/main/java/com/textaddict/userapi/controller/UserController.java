package com.textaddict.userapi.controller;

import com.textaddict.userapi.data.ArticleApiClient;
import com.textaddict.userapi.exceptions.UserNotFoundException;
import com.textaddict.userapi.model.User;
import com.textaddict.userapi.service.UserService;
import com.textaddict.userapi.ui.model.request.UserCreateRequest;
import com.textaddict.userapi.ui.model.response.ArticleResponse;
import com.textaddict.userapi.ui.model.response.UserCreateResponse;
import com.textaddict.userapi.ui.model.response.UserDetailResponse;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Getter
@Setter
@RefreshScope
public class UserController {
    @Value("${token.expiration.time}")
    private String tokenExpirationTime;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleApiClient articleApiClient;

    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User createdUser = userService.createUser(userCreateRequest);
        UserCreateResponse userCreateResponse = new ModelMapper().map(createdUser, UserCreateResponse.class);
        return new ResponseEntity<>(userCreateResponse, HttpStatus.CREATED);
    }

    @GetMapping("/exception")
    public String getException() {
        return "basarili tokenExpirationTime is: " + tokenExpirationTime;
        //if(true) throw new UserNotPermittedException("Special exception");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> getUserDetail(@PathVariable("id") String id) {
        Optional<User> user = userService.findUserById(UUID.fromString(id));
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with given identifier");
        }

        List<ArticleResponse> articles=articleApiClient.getArticles("1");
        UserDetailResponse userdetailed=new ModelMapper().map(user.get(), UserDetailResponse.class);
        userdetailed.setArticles(articles);
        return new ResponseEntity<>(userdetailed, HttpStatus.OK);
    }


}
