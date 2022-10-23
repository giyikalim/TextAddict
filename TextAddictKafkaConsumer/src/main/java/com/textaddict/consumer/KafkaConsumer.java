package com.textaddict.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textaddict.dto.ArticleDto;
import com.textaddict.dto.ArticlePageDto;
import org.apache.kafka.streams.kstream.KStream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private ArticleMongoRepository articleMongoRepository;

    /*
     *   consume the numbers received via kafka topic
     *   Consumer<T> makes this as kafka consumer of T
     * */

    @Bean
    public Consumer<KStream<String, String>> articleConsumer() {
        return stream -> stream.foreach((key, value) -> {
            System.out.println("Article event : " + value);

            try {
                ArticleDto article = OBJECT_MAPPER.readValue(value, ArticleDto.class);
                if (article.getPages() == null) {
                    saveArticle(article);
                } else {
                    updateArticle(article, article.getPages().get(0));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    ;

    @Transactional
    public void saveArticle(ArticleDto articleDto) {
        articleMongoRepository.findByUuid(articleDto.getUuid()).ifPresentOrElse(a -> {
            a.setHeader(articleDto.getHeader());
            a.setDescription(articleDto.getDescription());
            articleMongoRepository.save(a);
        }, () -> {
            ModelMapper modelMapper = new ModelMapper();
            ArticleDocument articleDocument = modelMapper.map(articleDto, ArticleDocument.class);
            articleMongoRepository.save(articleDocument);
        });
    }

    @Transactional
    public void updateArticle(ArticleDto articleDto, ArticlePageDto articlePageDto) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<ArticleDocument> articleDocument = articleMongoRepository.findByUuid(articleDto.getUuid());
        articleDocument.ifPresent(doc -> {
            //first page added
            if (doc.getPages() == null) {
                doc.setPages(Arrays.asList(articlePageDto));
                articleMongoRepository.save(doc);
                return;
            }
            //if page exist update other else create new page
            doc.getPages().stream().filter(p -> p.getUuid().equals(articlePageDto.getUuid())).findFirst().ifPresentOrElse(page -> {
                page.setText(articlePageDto.getText());
                page.setModifiedDate(articlePageDto.getModifiedDate());
                articleMongoRepository.save(doc);
            }, () -> {
                List<ArticlePageDto> pages = doc.getPages() == null ? new ArrayList<>() : doc.getPages();
                pages.add(articlePageDto);
                articleMongoRepository.save(doc);
            });
        });
    }

}