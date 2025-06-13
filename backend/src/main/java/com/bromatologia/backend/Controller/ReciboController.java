package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Recibo;
import com.bromatologia.backend.Service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recibo")
@CrossOrigin("http://localhost:4200/")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;


    @GetMapping("/")
    public ResponseEntity<List<Recibo>> obtenerRecibos() {
        List<Recibo> listaRecibos = reciboService.obtenerRecibos();
        if(listaRecibos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaRecibos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recibo> obtenerReciboPorId(@PathVariable long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Recibo buscado = reciboService.obtenerReciboPorId(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Recibo> agregarRecibo(@RequestBody Recibo recibo) {
        if(recibo == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Recibo nuevoRecibo = reciboService.crearRecibo(recibo);
        return new ResponseEntity<>(nuevoRecibo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recibo> eliminarRecibo(@PathVariable long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        reciboService.eliminarReciboPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
