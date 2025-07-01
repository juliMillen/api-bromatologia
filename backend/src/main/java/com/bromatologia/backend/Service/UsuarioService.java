package com.bromatologia.backend.Service;

import com.bromatologia.backend.DTO.UsuarioUpdateDTO;
import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Enums.Rol;
import com.bromatologia.backend.Exception.UsuarioException;
import com.bromatologia.backend.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(long id){
        return obtenerUsuarioExistente(id);
    }

    public Usuario crearUsuario(Usuario usuario){
        if(usuario == null){
            throw new UsuarioException("El usuario no puede ser nulo");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario modificarUsuario(long id, UsuarioUpdateDTO dto){
        if(id <= 0){
            throw new UsuarioException("El id del usuario no puede ser nulo");
        }

        if(dto == null){
            throw new UsuarioException("El usuario no puede ser nulo");
        }

        Usuario aModificar = obtenerUsuarioExistente(id);

        if(dto.getUsername() != null && !dto.getUsername().trim().isEmpty()){
            String nuevoUsername = dto.getUsername().trim();
            if(!nuevoUsername.equals(aModificar.getUsername())){
                validarUsername(nuevoUsername);
                aModificar.setUsername(nuevoUsername);
            }
        }

        if(dto.getPassword() != null){
            String nuevoPassword = dto.getPassword().trim();
            validarPassword(nuevoPassword);
            aModificar.setPassword(passwordEncoder.encode(nuevoPassword)); //encriptamos la nueva contraseña
        }

        String nuevoRol = dto.getRol().name(); //convertir enum --> string
        validarRol(nuevoRol); // validar ese string
        aModificar.setRol(Rol.valueOf(nuevoRol)); // Volver a conver string --> enum
        return usuarioRepository.save(aModificar);
    }

    public void eliminarUsuario(long id){
        if(id <= 0){
            throw new UsuarioException("El id ha sido invalido");
        }
        Usuario aEliminar = obtenerUsuarioExistente(id);
        usuarioRepository.delete(aEliminar);
    }


    private void validarUsername(String username){
        if( username == null ||username.trim().isEmpty()){
            throw new UsuarioException("El nombre de usuario no puede ser nulo");
        }

        if(usuarioRepository.findByUsername(username).isPresent()){
            throw new UsuarioException("El nombre de usuario ya existe");
        }
    }

    private void validarPassword(String password){
        if(password == null || password.trim().isEmpty()){
            throw new UsuarioException("El password no puede ser nulo");
        }
    }

    private void validarRol(String rol){
        if(rol == null || rol.trim().isEmpty()){
            throw new UsuarioException("El rol no puede ser nulo");
        }

        try{
            Rol.valueOf(rol.toUpperCase()); //Aseguro que sea un nombre valido del enum
        }catch(IllegalArgumentException e){
            throw new UsuarioException("El rol no es valido");
        }
    }

    public Usuario obtenerUsuarioExistente(long id){
        if(id <= 0){
            throw new UsuarioException("El id del usuario no puede ser negativo");
        }
        return usuarioRepository.findById(id).orElseThrow(()-> new UsuarioException("Usuario no encontrado"));
    }

}

