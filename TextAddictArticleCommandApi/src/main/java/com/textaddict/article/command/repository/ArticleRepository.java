package com.textaddict.article.command.repository;

import com.textaddict.article.command.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends CrudRepository<Article, UUID> {
    @Query("select distinct a from Article a left join fetch a.pages p where a.createdBy = ?1 order by a.createdDate desc")
    public List<Article> findArticlesByCreatedBy(String id);
}
