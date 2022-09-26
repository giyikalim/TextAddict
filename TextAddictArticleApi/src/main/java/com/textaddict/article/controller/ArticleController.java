package com.textaddict.article.controller;

import com.textaddict.article.exceptions.UserNotPermittedException;
import com.textaddict.article.model.Article;
import com.textaddict.article.service.ArticleService;
import com.textaddict.article.ui.model.request.ArticleResponse;
import com.textaddict.article.ui.model.request.UserRequest;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
@Getter
@Setter
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Value("${spring.application.instance_id}")
    private String instanceId;

    @GetMapping("/hello/{name}")
    public ResponseEntity<String> sayHello(@PathVariable String name, @RequestParam(value = "age", required = true, defaultValue = "25") int userAge) {
        return new ResponseEntity<>("Hello " + name + " your age is " + userAge + " your host: " + instanceId, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<ArticleResponse>> sayHello(@PathVariable String id) {
        List<Article> articles = Arrays.asList(
                new Article("Yazmak1", "Yazmak en guzel seydir1"),
                new Article("Yazmak2", "Yazmak en guzel seydir2"),
                new Article("Yazmak3", "Yazmak en guzel seydir3"));
        ModelMapper modelMapper=new ModelMapper();
        List<ArticleResponse> articlesResponse=articles.stream().map(a->modelMapper.map(a, ArticleResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(articlesResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/hello", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> sayHelloPost(@Valid @RequestBody UserRequest userRequest) {
        if (userRequest.getAge() < 20) {
            throw new UserNotPermittedException("Users belong the age 18 is not permitted to do that operation!");
        }
        return new ResponseEntity<String>("user created", HttpStatus.CREATED);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) {
        Article articleSaved = articleService.saveArticle(article);
        return new ResponseEntity<>(articleSaved, HttpStatus.CREATED);
    }
}
