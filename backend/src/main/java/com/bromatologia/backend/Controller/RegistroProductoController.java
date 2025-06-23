package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Exception.RegistroProductoException;
import com.bromatologia.backend.Service.RegistroProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registroProducto")
//@CrossOrigin("http://localhost:4200/")
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

    @PostMapping("/{id}/producto")
    public ResponseEntity<Producto> asignarProducto(@PathVariable long id, @RequestBody @Valid Producto producto) {
        Producto nuevo = registroProductoService.asignarProducto(id, producto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/mantenimiento")
    public ResponseEntity<Mantenimiento> agregarMantenimiento(@PathVariable Long id, @RequestBody @Valid Mantenimiento mantenimiento) {
        Mantenimiento nuevo = registroProductoService.agregarMantenimiento(id, mantenimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/registroEstablecimiento")
    public ResponseEntity<RegistroEstablecimiento> agregarRegistroEstablecimiento(@PathVariable Long id, @RequestBody @Valid RegistroEstablecimiento regisroEstablecimiento) {
        RegistroEstablecimiento nuevo = registroProductoService.asignarRegistroEstablecimiento(id,regisroEstablecimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroProducto> eliminarRegistroProducto(@PathVariable long id) {
        registroProductoService.eliminarRegistroProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
