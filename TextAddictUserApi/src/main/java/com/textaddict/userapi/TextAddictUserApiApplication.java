package com.textaddict.userapi;

import com.textaddict.userapi.exceptions.FeignErrorDecoder;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
public class TextAddictUserApiApplication {
    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(TextAddictUserApiApplication.class, args);
    }


    @Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("default")
    Logger.Level feignDefaultLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @Profile("!production")
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    FeignErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    @Profile("prod")
    public String createProductBean() {
        System.out.println("Product bean" + environment.getProperty("myapplication.profile"));
        return "Production Bean";
    }

    @Bean
    @Profile("default")
    public String createDefaultBean() {
        System.out.println("Development bean" + environment.getProperty("myapplication.profile"));
        return "Development Bean";
    }

    @Bean
    @Profile("!prod")
    public String createNonProductionBean() {
        System.out.println("Non production bean" + environment.getProperty("myapplication.profile"));
        return "Non production Bean";
    }
}
