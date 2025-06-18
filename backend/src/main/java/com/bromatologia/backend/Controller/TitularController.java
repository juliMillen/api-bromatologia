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

    @GetMapping("/{cuit_Titular}")
    public ResponseEntity<Titular> obtenerTitular(@PathVariable long cuit_Titular) {
        Titular buscado = titularService.obtenerTitularPorCuit(cuit_Titular);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Titular> crearTitular(@RequestBody @Valid Titular titular) {
        Titular nuevoTitular = titularService.crearTitular(titular);
        return new ResponseEntity<>(nuevoTitular, HttpStatus.CREATED);
    }

    @PutMapping("/{cuit_Titular}")
    public ResponseEntity<Titular> actualizarTitular(long cuit_Titular,@RequestBody @Valid Titular titular) {

        Titular nuevoTitular = titularService.actualizarTitular(cuit_Titular,titular);
        return new ResponseEntity<>(nuevoTitular, HttpStatus.OK);
    }

    @DeleteMapping("/{cuit_Titular}")
    public ResponseEntity<String> eliminarTitular(@PathVariable long cuit_Titular) {
        titularService.eliminarTitular(cuit_Titular);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
