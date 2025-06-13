package com.bromatologia.backend.Controller;


import com.bromatologia.backend.Entity.Titular;
import com.bromatologia.backend.Service.TitularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titular")
@CrossOrigin("http://localhost:4200/")
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

    @GetMapping("/{id}")
    public ResponseEntity<Titular> obtenerTitular(@PathVariable long cuit) {
        if(cuit <= 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Titular buscado = titularService.obtenerTitularPorCuit(cuit);
        if(buscado == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(buscado, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Titular> crearTitular(@RequestBody Titular titular) {
        if(titular == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Titular nuevoTitular = titularService.crearTitular(titular);
        return new ResponseEntity<>(nuevoTitular, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Titular> actualizarTitular(@RequestBody Titular titular) {
        if(titular == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Titular nuevoTitular = titularService.actualizarTitular(titular);
        return new ResponseEntity<>(nuevoTitular, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTitular(@PathVariable long cuit) {
        if(cuit <= 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        titularService.eliminarTitular(cuit);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
