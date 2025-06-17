package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Exception.UsuarioException;
import com.bromatologia.backend.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Usuario modificarUsuario(long id,Usuario usuario){
        if(id <= 0){
            throw new UsuarioException("El id del usuario no puede ser nulo");
        }

        if(usuario == null){
            throw new UsuarioException("El usuario no puede ser nulo");
        }

        Usuario aModificar = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioException("Usuario no encontrado"));

        if(usuario.getUsername() != null){
            String nuevoUsername = usuario.getUsername();
            if(nuevoUsername.isEmpty()){
                throw new UsuarioException("El nombre de usuario no puede ser nulo");
            }

            if(!nuevoUsername.equals(usuario.getUsername())){
                Optional<Usuario> usuarioConMismoNombre = usuarioRepository.findByUsername(nuevoUsername);
                if(usuarioConMismoNombre.isPresent()){
                    throw new UsuarioException("El nombre de usuario ya existe");
                }
            }

            aModificar.setUsername(nuevoUsername);
        }

        if(usuario.getPassword() != null){
            String nuevoPassword = usuario.getPassword().trim();
            if(!nuevoPassword.isEmpty()){
                aModificar.setPassword(nuevoPassword);
            }
        }

        if(usuario.getRol() != null){
            aModificar.setRol(usuario.getRol());
        }
        return usuarioRepository.save(aModificar);
    }

    public void eliminarUsuario(Long id){
        if(id <= 0){
            throw new UsuarioException("El id ha sido invalido");
        }
        Usuario aEliminar = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioException("Usuario no encontrado"));
        usuarioRepository.delete(aEliminar);
    }
}
