package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Exception.UsuarioException;
import com.bromatologia.backend.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id){
        if(id <= 0){
            throw new UsuarioException("El id del usuario no puede ser negativo");
        }
        return usuarioRepository.findById(id).orElseThrow(()-> new UsuarioException("Usuario no encontrado"));
    }

    public Usuario crearUsuario(Usuario usuario){
        if(usuario == null){
            throw new UsuarioException("El usuario no puede ser nulo");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario modificarUsuario(Usuario usuario){
        if(usuario != null){
            Usuario nuevoUsuario = usuarioRepository.findById(usuario.getId()).orElseThrow(()-> new UsuarioException("Usuario no encontrado"));
            nuevoUsuario.setUsername(usuario.getUsername());
            nuevoUsuario.setPassword(usuario.getPassword());
            nuevoUsuario.setRol(usuario.getRol());
            return usuarioRepository.save(nuevoUsuario);
        }
        return null;
    }

    public void eliminarUsuario(Long id){
        if(id <= 0){
            throw new UsuarioException("El id ha sido invalido");
        }
        Usuario aEliminar = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioException("Usuario no encontrado"));
        usuarioRepository.delete(aEliminar);
    }
}
