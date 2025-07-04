package com.bromatologia.backend.Security;

import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getTipoRol());
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(authority)
        );
    }
}
