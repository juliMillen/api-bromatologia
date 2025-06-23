package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Mantenimiento;
import com.bromatologia.backend.Entity.Tramite;
import com.bromatologia.backend.Service.MantenimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mantenimiento")
//@CrossOrigin("http://localhost:4200/")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping("/")
    public ResponseEntity<List<Mantenimiento>>obtenerMantenimientos(){
        List<Mantenimiento> listaMantenimientos = mantenimientoService.obtenerMantenimientos();
        return new ResponseEntity<>(listaMantenimientos, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Mantenimiento> obtenerMantenimientoPorId(@PathVariable long id){
        Mantenimiento buscado = mantenimientoService.obtenerMantenimiento(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @GetMapping("/{id}/tramites")
    public ResponseEntity<List<Tramite>> obtenerTramitesPorMantenimiento(@PathVariable long id) {
        Mantenimiento buscado = mantenimientoService.obtenerMantenimiento(id);
        return new ResponseEntity<>(buscado.getTramites(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Mantenimiento> registrarMantenimiento(@RequestBody @Valid Mantenimiento mantenimiento){

        Mantenimiento registrado = mantenimientoService.registrarMantenimiento(mantenimiento);
        return new ResponseEntity<>(registrado, HttpStatus.CREATED);
    }

    @PostMapping("{id}/tramites")
    public ResponseEntity<Tramite> agregarTramite(@PathVariable long id, @RequestBody @Valid Tramite tramite){
        Tramite nuevo = mantenimientoService.agregarTramite(id, tramite);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mantenimiento> eliminarMantenimiento(@PathVariable long id){
        mantenimientoService.eliminarMantenimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
