package com.bethen.bethen.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtil {

    String WORKING_SECRET = "l31XkIh133kw69MRF5aY+0mnKNxhkkkBQ6iaAyWZjlI=";
    Key uDeyWhine = Keys.hmacShaKeyFor(WORKING_SECRET.getBytes(StandardCharsets.UTF_8));


    //Generate Token
    public String generateToken(JwtObjectForGen userData){

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userData.getRole());
        claims.put("firstName", userData.getFirstName());
        claims.put("lastName", userData.getLastName());
        claims.put("email", userData.getEmail());
        claims.put("userId", userData.getUserId());
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(userData.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(uDeyWhine, SignatureAlgorithm.HS256)
                .compact();
    }

    //Extract email from the token
    public String extractTokenClaims(String token){
        return extractAllClaims(token).getSubject();

    }

    //Extract user id from token
    public Object extractId(String token){
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(uDeyWhine)
                .build()
                .parseClaimsJws(token);

        Claims claims = jwsClaims.getBody();
        String userId = claims.get("userId", String.class);
        return  userId;
    }



    //Implement Extract all claims
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(uDeyWhine)
                .build()
                .parseClaimsJws(token)
                .getBody();


    }

    //Check token expiration
    private boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    //Validate token
    public  boolean validateToken(String token, String email){
        final String extractedEmail = extractTokenClaims(token);
        return  (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    //Get all total claims
    public Object getTotalClaims(String token){
        return extractAllClaims(token);
    }




}
