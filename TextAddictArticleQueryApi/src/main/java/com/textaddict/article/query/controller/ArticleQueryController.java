package com.textaddict.article.query.controller;

import com.textaddict.article.query.model.ArticleDocument;
import com.textaddict.article.query.service.ArticleQueryService;
import com.textaddict.article.query.ui.model.request.ArticleResponse;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
@Getter
@Setter
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "false")
public class ArticleQueryController {

    @Autowired
    private ArticleQueryService articleService;

    @GetMapping("/header/{header}")
    public ResponseEntity<List<ArticleResponse>> findArticlesByHeader(@PathVariable String header) {
        List<ArticleDocument> articles=articleService.findArticlesByHeader(header);
        ModelMapper modelMapper=new ModelMapper();
        List<ArticleResponse> articlesResponse=articles.stream().map(a->modelMapper.map(a, ArticleResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(articlesResponse, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('AUTHOR')")
}
