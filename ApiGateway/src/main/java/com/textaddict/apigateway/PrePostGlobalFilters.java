package com.textaddict.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;


@Configuration
public class PrePostGlobalFilters {
    final Logger logger= LoggerFactory.getLogger(PrePostGlobalFilters.class);

    @Order(1)
    @Bean
    public GlobalFilter firstPreAndPostFilter(){
        return (exchange, chain) -> {
            logger.info("First pre glabal filter executed");
            String path=exchange.getRequest().getPath().toString();
            logger.info("Path is: "+path);
            HttpHeaders headers=exchange.getRequest().getHeaders();
            for (String key :
                    headers.keySet()) {
                logger.info(key+": "+headers.getFirst(key));
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info("First post filter is executed");
            }));
        };
    }
}
