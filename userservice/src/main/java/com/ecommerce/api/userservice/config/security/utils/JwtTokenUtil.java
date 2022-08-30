package com.ecommerce.api.userservice.config.security.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.hql.internal.ast.tree.IdentNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value(value = "${jwt.secret}") // gets value from jwt.secret inside application.properties // @Value -> used to access variables inside application.properties
    private String secret;

    @Value(value = "${jwt.validity}")
    private String validity;

    public String generateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setSubject("oi")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(validity)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}
