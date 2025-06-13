package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Tramite;
import com.bromatologia.backend.Service.TramiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tramite")
@CrossOrigin("http://localhost:4200/")
public class TramiteController {
    @Autowired
    private TramiteService tramiteService;


    @GetMapping("/")
    public ResponseEntity<List<Tramite>> obtenerTramites() {
        List<Tramite> listaTramites = tramiteService.obtenerTramites();
        if(listaTramites.isEmpty()) {
            return new ResponseEntity<>(listaTramites, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaTramites, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tramite> obtenerTramite(@PathVariable long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Tramite buscado = tramiteService.obtenerTramitePorId(id);
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Tramite> crearTramite(@RequestBody Tramite tramite) {
        if(tramite == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Tramite nuevoTramite = tramiteService.crearTramite(tramite);
        return new ResponseEntity<>(nuevoTramite, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tramite> eliminarTramite(@PathVariable long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        tramiteService.eliminarTramite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
