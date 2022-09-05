package com.api.ecommerce.userservice.config.security.filters;

import com.api.ecommerce.userservice.config.security.EmailPasswordAuthenticationToken;
import com.api.ecommerce.userservice.config.security.utils.JwtTokenUtil;
import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.services.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

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

        String email = null;

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

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserModel userModel = authenticationService.loadUserByEmail(jwtTokenUtil.getSubjectFromToken(token));

            if(jwtTokenUtil.isTokenValid(token, userModel)){

                // Refresh the token when already authenticated
                String newJwtToken = jwtTokenUtil.generateToken(new HashMap<>(), userModel.getEmail());
                response.setHeader("Authorization", newJwtToken);

                EmailPasswordAuthenticationToken authenticationToken = new EmailPasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword(), Boolean.TRUE);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
