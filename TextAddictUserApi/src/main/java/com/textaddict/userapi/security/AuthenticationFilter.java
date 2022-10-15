package com.textaddict.userapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textaddict.userapi.service.UserService;
import com.textaddict.userapi.ui.model.request.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private Long tokenExpirationTime;
    private String tokenSecret;
    private String loginUrlPath;
    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Long tokenExpirationTime, String tokenSecret, String loginUrlPath) {
        this.userService=userService;
        this.tokenExpirationTime=tokenExpirationTime;
        this.tokenSecret=tokenSecret;
        this.loginUrlPath=loginUrlPath;
        setFilterProcessesUrl(loginUrlPath);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        try {
            UsernamePasswordAuthenticationToken authRequest
                    = getAuthRequest(request);

            return getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        com.textaddict.userapi.model.User user = userService.getUserDetailsByEmail(((User) authResult.getPrincipal()).getUsername());

        String roles=user.getRoles().stream().map(r->"ROLE_"+r.getRole().toUpperCase()).collect(Collectors.joining(","));

        String token = Jwts.builder().claim("roles", roles).setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS512, tokenSecret).compact();

        response.addHeader("token", token);
        response.addHeader("userId",user.getId().toString());
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(
            HttpServletRequest request) throws IOException {

        LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

        return new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>());
    }

}