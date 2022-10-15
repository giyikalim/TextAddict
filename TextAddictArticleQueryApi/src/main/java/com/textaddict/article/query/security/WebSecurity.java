package com.textaddict.article.query.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Environment environment;

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${login.url.path}")
    private String loginUrlPath;

    @Value("${gateway.ip}")
    private String gatewayIp;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/circuitbreakerevents").permitAll()
                .antMatchers(HttpMethod.GET, "/articles/**").permitAll()
                //http.authorizeRequests().antMatchers("/**").ip(gatewayIp)
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthorizationFilter());
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

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }*/

    /*private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, tokenExpirationTime, tokenSecret, loginUrlPath);
        return authenticationFilter;
    }*/

    private AuthorizationFilter getAuthorizationFilter() throws Exception {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(authenticationManager(), environment);
        return authorizationFilter;
    }
}