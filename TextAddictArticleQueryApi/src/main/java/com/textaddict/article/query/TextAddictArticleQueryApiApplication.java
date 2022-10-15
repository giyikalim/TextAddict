package com.textaddict.article.query;

import com.textaddict.article.query.repository.ArticleMongoRepository;
import com.textaddict.article.query.security.Auditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableMongoRepositories
public class TextAddictArticleQueryApiApplication implements CommandLineRunner {
    @Autowired
    private ArticleMongoRepository articleMongoRepository;

    @Override
    public void run(String... args) throws Exception {
        /*ArticleDocument articleDocument=new ArticleDocument();
        articleDocument.setText("this is first text");
        articleDocument.setHeader("this is first header");
        articleMongoRepository.save(articleDocument);*/
    }

    public static void main(String[] args) {
        SpringApplication.run(TextAddictArticleQueryApiApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<String> auditor() {
        return new Auditor();
    }
}
