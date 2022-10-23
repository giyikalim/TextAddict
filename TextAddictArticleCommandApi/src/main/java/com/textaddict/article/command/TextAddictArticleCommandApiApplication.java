package com.textaddict.article.command;

import com.textaddict.article.command.security.Auditor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
public class TextAddictArticleCommandApiApplication{

    @Bean
    public FluxSinkImpl fluxSinkConsumer(){
        return new FluxSinkImpl();
    }

    @Bean
    public Supplier<Flux<String>> articleProducer(FluxSinkImpl fluxSinkConsumer){

        Flux<String> articleFlux = Flux.create(fluxSinkConsumer).share();

        return () -> articleFlux;
    };

    public static void main(String[] args) {
        SpringApplication.run(TextAddictArticleCommandApiApplication.class, args);
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
