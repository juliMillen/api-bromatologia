package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioService.obtenerUsuarios();
        if (listaUsuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable long id) {
        Usuario buscado = usuarioService.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        Usuario aModificar = usuarioService.modificarUsuario(usuario);
        return new ResponseEntity<>(aModificar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
