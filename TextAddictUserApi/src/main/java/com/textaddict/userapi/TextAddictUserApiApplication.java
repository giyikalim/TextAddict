package com.textaddict.userapi;

import com.textaddict.userapi.exceptions.FeignErrorDecoder;
import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableWebSecurity
@EnableFeignClients
public class TextAddictUserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextAddictUserApiApplication.class, args);
	}


	@Bean
	public BCryptPasswordEncoder getbCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}

	@Bean
	FeignErrorDecoder getFeignErrorDecoder(){
		return  new FeignErrorDecoder();
	}
}
