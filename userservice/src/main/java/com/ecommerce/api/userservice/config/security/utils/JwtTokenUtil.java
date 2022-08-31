package com.ecommerce.api.userservice.config.security.utils;

import com.ecommerce.api.userservice.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

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
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(validity)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Will return the email from the token
    public String getSubjectFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpireDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return getExpireDateFromToken(token).after(new Date());
    }

    public Boolean isTokenValid(String token, UserModel userModel){
        if(isTokenExpired(token) || !getSubjectFromToken(token).equals(userModel.getEmail()))
            return false;
        return true;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
