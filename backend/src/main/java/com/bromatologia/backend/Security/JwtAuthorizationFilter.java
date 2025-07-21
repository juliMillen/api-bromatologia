package com.bromatologia.backend.Security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    //filtro de autorizacion
    @Autowired
    private JwtUtils jwtUtils;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*String path= request.getServletPath();

        if(path.equals("/login") || path.equals("/register")){
            filterChain.doFilter(request, response);
            return;
        }*/

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "").trim(); //limpiar el token

        try{
            if(jwtUtils.validarToken(token)){
                Claims claims = jwtUtils.getClaims(token);
                String username = claims.getSubject();
                List<String> roles = claims.get("authorities", List.class);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }else{
                //Token invalido
                SecurityContextHolder.clearContext();
            }

        }catch(Exception e){
            //Si el token es invalido o ya expiro, limpiamos el contexto
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
