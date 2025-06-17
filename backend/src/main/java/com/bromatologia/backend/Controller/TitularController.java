package com.bromatologia.backend.Controller;


import com.bromatologia.backend.Entity.Titular;
import com.bromatologia.backend.Service.TitularService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titular")
//@CrossOrigin("http://localhost:4200/")
public class TitularController {
    @Autowired
    private TitularService titularService;

    @GetMapping("/")
    public ResponseEntity<List<Titular>> obtenerTitulares() {
        List<Titular> listaTitulares = titularService.obtenerTitular();
        if(listaTitulares.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaTitulares, HttpStatus.OK);
    }

    @GetMapping("/{cuit}")
    public ResponseEntity<Titular> obtenerTitular(@PathVariable long cuit) {
        Titular buscado = titularService.obtenerTitularPorCuit(cuit);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Titular> crearTitular(@RequestBody @Valid Titular titular) {
        Titular nuevoTitular = titularService.crearTitular(titular);
        return new ResponseEntity<>(nuevoTitular, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Titular> actualizarTitular(@RequestBody @Valid Titular titular) {

        Titular nuevoTitular = titularService.actualizarTitular(titular);
        return new ResponseEntity<>(nuevoTitular, HttpStatus.OK);
    }

    @DeleteMapping("/{cuit}")
    public ResponseEntity<String> eliminarTitular(@PathVariable long cuit) {
        titularService.eliminarTitular(cuit);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
