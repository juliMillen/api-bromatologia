package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.EmpresaDTO;
import com.bromatologia.backend.DTO.EstablecimientoDTO;
import com.bromatologia.backend.DTO.UsuarioDTO;
import com.bromatologia.backend.DTO.UsuarioUpdateDTO;
import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Titular;
import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Enums.Rol;
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
        List<UsuarioDTO> listaUsuarios = usuarioService.obtenerUsuarios()
                .stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
        if (listaUsuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable long id) {
        Usuario buscado = usuarioService.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(new UsuarioDTO(buscado), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UsuarioDTO> crearUsuarioAdmin( @Valid @RequestBody Usuario usuario) {
        Usuario admin = usuarioService.crearUsuarioAdmin(usuario);
        return new ResponseEntity<>(new UsuarioDTO(admin), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<UsuarioDTO> crearUsuario( @Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        UsuarioDTO dto = new UsuarioDTO(nuevoUsuario);
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
}
