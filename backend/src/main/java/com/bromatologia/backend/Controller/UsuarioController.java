package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.*;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioService.obtenerUsuarios();
        List<UsuarioDTO> listaDTO = listaUsuarios
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        if (listaUsuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable long id) {
        Usuario buscado = usuarioService.obtenerUsuarioPorId(id);
        UsuarioDTO usuarioDTO = convertirADTO(buscado);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UsuarioDTO> crearUsuarioAdmin( @Valid @RequestBody Usuario usuario) {
        Usuario admin = usuarioService.crearUsuarioAdmin(usuario);
        UsuarioDTO usuarioDTO = convertirADTO(admin);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<UsuarioDTO> crearUsuario( @Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        UsuarioDTO dto = convertirADTO(nuevoUsuario);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable long id, @Valid @RequestBody UsuarioUpdateDTO dto) {
        Usuario aModificar = usuarioService.modificarUsuario(id,dto);
        return new ResponseEntity<>(aModificar, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //metodo de mapeo DTO <---> entidad
    private UsuarioDTO convertirADTO(Usuario entidad){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entidad.getId());
        dto.setUsername(entidad.getUsername());
        dto.setRol(entidad.getRol().getTipoRol());

        /*Rol
        Rol rol = entidad.getRol();
        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(rol.getIdRol());
        rolDTO.setTipoRol(rol.getTipoRol());
        dto.setRol(rolDTO);*/
        return dto;
    }

}
