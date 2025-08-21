package com.enterprise.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
   @Value("${security.jwt.secret-key}")
    private String SECRET;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    public Key getSigningKey(){
        byte[] decodedKey= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
  public Claims extractAllClaims(String token){
      return Jwts
              .parser()
              .setSigningKey(getSigningKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
  }

  public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
  }
  public  String extractEmail(String token){
        return extractAllClaims(token).getSubject();
  }
  public String generateToken(Map<String,Object> claims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256,getSigningKey())
                .compact();

  }
  public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
  }
    public boolean isTokenValid(String token,UserDetails userDetails){
        return extractEmail(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
    return extractClaim(token,Claims::getExpiration).before(new Date());
    }


    public Date extractExpirationDate(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }
}
