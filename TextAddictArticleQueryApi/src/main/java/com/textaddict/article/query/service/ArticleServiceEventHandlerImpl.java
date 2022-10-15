package com.textaddict.article.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textaddict.article.query.model.ArticleDocument;
import com.textaddict.article.query.repository.ArticleMongoRepository;
import com.textaddict.dto.ArticleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceEventHandlerImpl implements ArticleServiceEventHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private ArticleMongoRepository articleMongoRepository;

    @KafkaListener(topics = "article-service-event")
    public void consume(String articleStr) {
        try{
            ArticleDto article = OBJECT_MAPPER.readValue(articleStr, ArticleDto.class);
            createArticle(article);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void createArticle(ArticleDto articleDto){
        ModelMapper modelMapper=new ModelMapper();
        ArticleDocument articleDocument=modelMapper.map(articleDto, ArticleDocument.class);
        articleMongoRepository.save(articleDocument);
    }
}