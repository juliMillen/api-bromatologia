package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.RegistroProducto;
import com.bromatologia.backend.Exception.RegistroProductoException;
import com.bromatologia.backend.Service.RegistroProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registroProducto")
@CrossOrigin("http://localhost:4200/")
public class RegistroProductoController {
    @Autowired
    private RegistroProductoService registroProductoService;

    @GetMapping("/")
    public ResponseEntity<List<RegistroProducto>> obtenerRegistrosProductos() {
       List<RegistroProducto> listaRegistrosProd = registroProductoService.obtenerRegistrosProducto();
       return new ResponseEntity<>(listaRegistrosProd, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroProducto> obtenerRegistroProductoById(@PathVariable long id) {
        RegistroProducto buscado = registroProductoService.obtenerRegistroProducto(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<RegistroProducto> guardarRegistroProducto(@RequestBody RegistroProducto registroProducto) {
        RegistroProducto nuevoRegistroProducto = registroProductoService.guardarRegistroProducto(registroProducto);
        return new ResponseEntity<>(nuevoRegistroProducto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroProducto> eliminarRegistroProducto(@PathVariable long id) {
        registroProductoService.eliminarRegistroProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
