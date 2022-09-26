package com.textaddict.userapi.security;

import com.textaddict.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${token.expiration.time}")
    private Long tokenExpirationTime;

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${login.url.path}")
    private String loginUrlPath;

    @Value("${gateway.ip}")
    private String gatewayIp;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/circuitbreakerevents").permitAll()
        //http.authorizeRequests().antMatchers("/**").ip(gatewayIp)
                .and()
                .addFilter(getAuthenticationFilter());
    }

    /*protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(environment.getProperty("api.users.actuator.url.path")).permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path")).permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path")).permitAll()
                .anyRequest().authenticated()
        //http.authorizeRequests().antMatchers("/**").ip(gatewayIp)
                .and()
                .addFilter(getAuthenticationFilter());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws  Exception{
        AuthenticationFilter authenticationFilter=new AuthenticationFilter(authenticationManager(), userService, tokenExpirationTime, tokenSecret, loginUrlPath);
        return authenticationFilter;
    }
}