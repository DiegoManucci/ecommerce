package com.ecommerce.api.userservice.config.security;

import org.hibernate.annotations.Filter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationToken = request.getHeader("Authorization");

        if(authorizationToken != null)
            System.out.printf(authorizationToken);

        filterChain.doFilter(request, response);
    }
}
