package com.bromatologia.backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    //clase que genera el token

    private final String SECRET_KEY = "secretKey";
    private final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutos de duracion

    public String generateToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        String username;
        Collection<? extends GrantedAuthority> authorities;
        if (principal instanceof User) {
            User user = (User) principal;
            username = user.getUsername();
            authorities = user.getAuthorities();
        }else{
            username = principal.toString();
            authorities = Collections.emptySet();
        }


        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();

    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token invalido" + e);
        }
    }

    //Metodo adicional para validar token
    public boolean validarToken(String token){
        try{
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }
}
