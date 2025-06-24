package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Service.EstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establecimiento")
//@CrossOrigin("http://localhost:4200/")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @GetMapping("/")
    public ResponseEntity<List<Establecimiento>> obtenerEstablecimientos(){
        List<Establecimiento> listaEstablecimiento = establecimientoService.obtenerEstablecimientos();
        if(listaEstablecimiento.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaEstablecimiento, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> obtenerEstablecimiento(@PathVariable long id){
        Establecimiento buscado = establecimientoService.obtenerEstablecimientoPorId(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<List<Producto>> obtenerProductosDeEstablecimientos(@PathVariable long id) {
        Establecimiento buscado = establecimientoService.obtenerEstablecimientoPorId(id);
        return new ResponseEntity<>(buscado.getProductos(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<Establecimiento> crearEstablecimiento(@RequestBody @Valid Establecimiento establecimiento){

    Establecimiento nuevoEstablecimiento = establecimientoService.crearEstablecimiento(establecimiento);
    return new ResponseEntity<>(nuevoEstablecimiento, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idEstablecimiento}/productos/{idProducto}")
    public ResponseEntity<Producto> agregarProducto(@PathVariable long idEstablecimiento, @PathVariable long idProducto){
        Producto nuevo = establecimientoService.agregarProducto(idEstablecimiento, idProducto);
        return ResponseEntity.ok(nuevo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Establecimiento> eliminarEstablecimiento(@PathVariable long id){
        establecimientoService.eliminarEstablecimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
