package com.bromatologia.backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    //clase que genera el token

    private final String SECRET_KEY = "secretKey";
    private final long EXPIRATION_TIME = 864_000_000L;

    public String generateToken(Authentication authentication) {
        User user = (User)authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token.replace("Bearer ",""))
                .getBody();
    }
}
