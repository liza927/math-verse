package com.mathverse.core.security;

import com.mathverse.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String secretHash="qwertyuiopasdfghjklkzxcvbnm147852369";

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                        .claim("role",user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor(secretHash.getBytes()))
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretHash.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
