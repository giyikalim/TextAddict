package com.textaddict.article.command.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textaddict.article.command.model.Article;
import com.textaddict.article.command.model.ArticlePage;
import com.textaddict.article.command.repository.ArticlePageRepository;
import com.textaddict.article.command.repository.ArticleRepository;
import com.textaddict.dto.ArticleDto;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class ArticleCommandServiceImpl implements ArticleCommandService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticlePageRepository articlePageRepository;
    @Override
    public Article saveArticle(Article article) {
        Article articleCreated= articleRepository.save(article);
        ModelMapper modelMapper=new ModelMapper();
        ArticleDto articleDto=modelMapper.map(articleCreated, ArticleDto.class);
        articleDto.setUuid(articleCreated.getId().toString());
        raiseEvent(articleDto);
        return  articleCreated;
    }

    private void raiseEvent(ArticleDto dto){
        try{
            String value = OBJECT_MAPPER.writeValueAsString(dto);
            this.kafkaTemplate.sendDefault(dto.getUuid(), value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArticlePage createArticlePage(ArticlePage articlePage) {
        return articlePageRepository.save(articlePage);
    }

    @Override
    public Optional<Article> findById(UUID uuid) {
        return articleRepository.findById(uuid);
    }

    @Override
    public Optional<ArticlePage> findArticlePageById(UUID uuid) {
        return articlePageRepository.findById(uuid);
    }


}
