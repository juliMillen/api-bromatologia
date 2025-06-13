package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Mantenimiento;
import com.bromatologia.backend.Service.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mantenimiento")
@CrossOrigin("http://localhost:4200/")
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
        if(id <= 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Mantenimiento buscado = mantenimientoService.obtenerMantenimiento(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Mantenimiento> registrarMantenimiento(@RequestBody Mantenimiento mantenimiento){
        if(mantenimiento ==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Mantenimiento registrado = mantenimientoService.registrarMantenimiento(mantenimiento);
        return new ResponseEntity<>(registrado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mantenimiento> eliminarMantenimiento(@PathVariable long id){
        if(id <= 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        mantenimientoService.eliminarMantenimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
