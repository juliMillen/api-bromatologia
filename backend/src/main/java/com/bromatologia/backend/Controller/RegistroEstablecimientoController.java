package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.RegistroEstablecimiento;
import com.bromatologia.backend.Service.RegistroEstablecimientoService;
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
    public ResponseEntity<RegistroEstablecimiento> guardarRegistro(@RequestBody RegistroEstablecimiento registro) {
        RegistroEstablecimiento nuevoRegistro = registroEstablecimientoService.guardarRegistro(registro);
        return new ResponseEntity<>(nuevoRegistro, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroEstablecimiento> eliminarRegistro(@PathVariable long id) {
        registroEstablecimientoService.obtenerEstablecimientoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
