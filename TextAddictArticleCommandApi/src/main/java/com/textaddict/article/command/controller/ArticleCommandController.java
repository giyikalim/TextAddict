package com.textaddict.article.command.controller;

import com.textaddict.article.command.model.Article;
import com.textaddict.article.command.model.ArticlePage;
import com.textaddict.article.command.service.ArticleCommandService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
@Getter
@Setter
public class ArticleCommandController {

    @Autowired
    private ArticleCommandService articleService;

    @PreAuthorize("hasRole('AUTHOR')")
    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) {
        article.setPuplishNumber(1);
        Article articleSaved = articleService.saveArticle(article);
        return new ResponseEntity<>(articleSaved, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @PutMapping("/{id}")
    public ResponseEntity<String> createArticle(@Valid @RequestBody ArticlePage articlePage, @PathVariable String id) {
        Optional<Article> articleOptional = articleService.findById(UUID.fromString(id));

        if (!articleOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Article could not found");
        }

        if (articlePage.getId() != null) {
            Optional<ArticlePage> articlePageOptional = articleService.findArticlePageById(articlePage.getId());
            if (!articlePageOptional.isPresent()) {
                return ResponseEntity.badRequest().body("Article page could not found");
            }

            ArticlePage articlePageFound = articlePageOptional.get();
            articlePageFound.setText(articlePage.getText());
            //articlePageFound.setArticle(articleOptional.get());
            if(articlePageFound.getArticle().getId()!=articleOptional.get().getId()){
                return ResponseEntity.badRequest().body("Article page is not belong to article you gived");
            }
            articleService.createArticlePage(articlePageFound);
            return ResponseEntity.ok().body("Article page created successfully");
        } else {
            articlePage.setArticle(articleOptional.get());
            articleService.createArticlePage(articlePage);
            return ResponseEntity.ok().body("Article page created successfully");
        }
    }

}
