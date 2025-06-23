package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Service.RegistroEstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registroEstablecimiento")
//@CrossOrigin("http://localhost:4200/")
public class RegistroEstablecimientoController {
    @Autowired
    private RegistroEstablecimientoService registroEstablecimientoService;


    @GetMapping("/")
    public ResponseEntity<List<RegistroEstablecimiento>> getRegistroEstablecimiento() {
        List<RegistroEstablecimiento> listaRegistrosEst = registroEstablecimientoService.obtenerEstablecimientos();
        return new ResponseEntity<>(listaRegistrosEst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroEstablecimiento>getRegistroEstablecimientoById(@PathVariable long id) {
        RegistroEstablecimiento buscado = registroEstablecimientoService.obtenerEstablecimientoById(id);
        if(buscado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<RegistroEstablecimiento> guardarRegistro(@RequestBody @Valid RegistroEstablecimiento registro) {
        RegistroEstablecimiento nuevoRegistro = registroEstablecimientoService.guardarRegistro(registro);
        return new ResponseEntity<>(nuevoRegistro, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/empresa")
    public ResponseEntity<Empresa> asignarEmpresa(@PathVariable long id, @RequestBody @Valid Empresa empresa) {
        Empresa nueva = registroEstablecimientoService.asignarEmpresa(id,empresa);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/titular")
    public ResponseEntity<Titular> asignarTitular(@PathVariable Long id, @RequestBody @Valid Titular titular) {
        Titular nuevo = registroEstablecimientoService.asignarTitular(id,titular);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/establecimiento")
    public ResponseEntity<Establecimiento> asignarEstablecimiento(@PathVariable Long id, @RequestBody @Valid Establecimiento establecimiento) {
        Establecimiento nuevo = registroEstablecimientoService.asignarEstablecimiento(id,establecimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PostMapping("{id}/mantenimiento")
    public ResponseEntity<Mantenimiento> agregarMantenimiento(@PathVariable Long id, @RequestBody @Valid Mantenimiento mantenimiento) {
        Mantenimiento nuevo = registroEstablecimientoService.agregarMantenimiento(id, mantenimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroEstablecimiento> eliminarRegistro(@PathVariable long id) {
        registroEstablecimientoService.obtenerEstablecimientoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
