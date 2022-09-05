package com.api.ecommerce.userservice.config.security.utils;

import com.api.ecommerce.userservice.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
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
        return _getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpireDateFromToken(String token){
        return _getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return getExpireDateFromToken(token).before(new Date());
    }

    public Boolean isTokenValid(String token, UserModel userModel){
        return !isTokenExpired(token) && getSubjectFromToken(token).equals(userModel.getEmail());
    }

    private  <T> T _getClaimFromToken(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(_getAllClaimsFromToken(token));
    }

    private Claims _getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
