package com.ecommerce.api.userservice.config.security;

import com.ecommerce.api.userservice.config.security.utils.JwtTokenUtil;
import com.ecommerce.api.userservice.models.UserModel;
import com.ecommerce.api.userservice.services.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationService authenticationService;

    public AuthenticationFilter(JwtTokenUtil jwtTokenUtil, AuthenticationService authenticationService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader("Authorization");

        final String email;

        if(token != null){
            try {
                email = jwtTokenUtil.getSubjectFromToken(token);
            }
            catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT Token");
            }
            catch (ExpiredJwtException e){
                System.out.println("JWT Token Expired");
            }
        }

//        if (email != null && SecurityContextHolder.getContext().getAuthentication() != null){
//            UserModel userModel = authenticationService.loadUserByUsername()
//        }

        filterChain.doFilter(request, response);
    }
}
