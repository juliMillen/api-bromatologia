package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin("http://localhost:4200/")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public ResponseEntity<List<Producto>> listaProductos() {
        List<Producto> listaProductos = productoService.obtenerTodosProductos();
        if(listaProductos.isEmpty()){
            return new ResponseEntity<>(listaProductos, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaProductos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Producto buscado = productoService.obtenerProductoPorId(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        if(producto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Producto nuevoProducto = productoService.registrarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto) {
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Producto actualizado = productoService.editarProducto(producto);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> eliminarProducto(@PathVariable long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        productoService.eliminarProductoPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
