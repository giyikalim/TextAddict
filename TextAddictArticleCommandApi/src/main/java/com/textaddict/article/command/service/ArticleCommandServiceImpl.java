package com.textaddict.article.command.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textaddict.article.command.FluxSinkImpl;
import com.textaddict.article.command.data.UserApiClient;
import com.textaddict.article.command.model.Article;
import com.textaddict.article.command.model.ArticlePage;
import com.textaddict.article.command.repository.ArticlePageRepository;
import com.textaddict.article.command.repository.ArticleRepository;
import com.textaddict.dto.ArticleDto;
import com.textaddict.dto.ArticlePageDto;
import com.textaddict.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class ArticleCommandServiceImpl implements ArticleCommandService {

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private FluxSinkImpl fluxSinkConsumer;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //@Autowired
    //private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticlePageRepository articlePageRepository;

    @Override
    public Article saveArticle(Article article) {
        Article articleCreated = articleRepository.save(article);
        UserDto userDetails = userApiClient.getUserDetails(articleCreated.getCreatedBy());
        ModelMapper modelMapper = new ModelMapper();
        article.setPages(null);
        ArticleDto articleDto = modelMapper.map(articleCreated, ArticleDto.class);
        articleDto.setUuid(articleCreated.getId().toString());
        articleDto.setCreator(userDetails);
        raiseEvent(articleDto);
        return articleCreated;
    }

    private void raiseEvent(ArticleDto dto) {
        try {
            String value = OBJECT_MAPPER.writeValueAsString(dto);
            //this.kafkaTemplate.sendDefault(dto.getUuid(), value);
            fluxSinkConsumer.publishEvent(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArticlePage createArticlePage(ArticlePage articlePage) {
        ArticlePage articlePageSaved= articlePageRepository.save(articlePage);
        ArticleDto articleDto=new ArticleDto();
        articleDto.setUuid(articlePage.getArticle().getId().toString());

        ModelMapper modelMapper=new ModelMapper();
        TypeMap<ArticlePage, ArticlePageDto> propertyMapper = modelMapper.createTypeMap(ArticlePage.class, ArticlePageDto.class);
        propertyMapper.addMapping(ArticlePage::getId, ArticlePageDto::setUuid);

        List<ArticlePageDto> pages=Arrays.asList(modelMapper.map(articlePageSaved, ArticlePageDto.class));
        articleDto.setPages(pages);
        raiseEvent(articleDto);
        return articlePageSaved;
    }

    @Override
    public Optional<Article> findById(UUID uuid) {
        return articleRepository.findById(uuid);
    }

    @Override
    public Optional<ArticlePage> findArticlePageById(UUID uuid) {
        return articlePageRepository.findById(uuid);
    }


    @Override
    public List<Article> findArticlesByCreatedBy(String createdBy){
        return articleRepository.findArticlesByCreatedBy(createdBy);
    }
}
