package com.textaddict.article.command.controller;

import com.textaddict.article.command.exceptions.ArticleNotFoundException;
import com.textaddict.article.command.exceptions.ArticlePageNotFoundException;
import com.textaddict.article.command.model.Article;
import com.textaddict.article.command.model.ArticlePage;
import com.textaddict.article.command.service.ArticleCommandService;
import com.textaddict.dto.ArticleDto;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.ws.rs.PathParam;
import java.util.List;
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
    public ResponseEntity<Article> editArticle(@Valid @RequestBody Article article, @PathVariable String id) {
        Optional<Article> articleOptional = articleService.findById(UUID.fromString(id));
        if (!articleOptional.isPresent()) {
            throw new ArticleNotFoundException("Article could not found");
        } else {
            Article articleFound=articleOptional.get();
            articleFound.setHeader(article.getHeader());
            articleFound.setDescription(article.getDescription());
            Article articleSaved=articleService.saveArticle(articleFound);
            return new ResponseEntity<>(articleSaved, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @PutMapping("/page/{id}")
    public ResponseEntity<ArticlePage> createArticlePage(@Valid @RequestBody ArticlePage articlePage, @PathVariable String id) {
        Optional<Article> articleOptional = articleService.findById(UUID.fromString(id));

        if (!articleOptional.isPresent()) {
            throw  new ArticleNotFoundException("Article could not found");
        }

        if (articlePage.getId() != null) {
            Optional<ArticlePage> articlePageOptional = articleService.findArticlePageById(articlePage.getId());
            if (!articlePageOptional.isPresent()) {
                throw new ArticlePageNotFoundException("Article Page not found");
            }

            ArticlePage articlePageFound = articlePageOptional.get();
            articlePageFound.setText(articlePage.getText());
            //articlePageFound.setArticle(articleOptional.get());
            if (articlePageFound.getArticle().getId() != articleOptional.get().getId()) {
                throw new ArticlePageNotFoundException("Article Page not found");
            }
            ArticlePage articleSaved=articleService.createArticlePage(articlePageFound);
            return ResponseEntity.ok().body(articleSaved);
        } else {
            articlePage.setArticle(articleOptional.get());
            ArticlePage articleSaved=articleService.createArticlePage(articlePage);
            return ResponseEntity.ok().body(articleSaved);
        }
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @GetMapping("/author/{id}")
    public ResponseEntity<List<Article>> getAuthorArticles(@PathVariable("id") String id) {
        List<Article> articles = articleService.findArticlesByCreatedBy(id);
        return ResponseEntity.ok().body(articles);
    }
}
