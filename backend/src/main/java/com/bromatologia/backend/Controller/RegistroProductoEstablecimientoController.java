package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.RegistroProductoEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimientoId;
import com.bromatologia.backend.Service.RegistroProductoEstablecimientoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro-producto-establecimiento")
public class RegistroProductoEstablecimientoController {
    @Autowired
    private RegistroProductoEstablecimientoService registroProductoEstablecimientoService;


    @GetMapping("/")
    public ResponseEntity<List<RegistroProductoEstablecimiento>> obtenerRegistroProductoEstablecimiento() {
       List<RegistroProductoEstablecimiento> listaRegistros = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimiento();
       return new ResponseEntity<>(listaRegistros, HttpStatus.OK);
    }

    @GetMapping("/{idProducto}/{idEstablecimiento}")
    public ResponseEntity<RegistroProductoEstablecimiento> obtenerRegistroProductoEstablecimientoPorId(@PathVariable long idProducto,@PathVariable long idEstablecimiento){
        RegistroProductoEstablecimiento buscado = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimientoPorId(idProducto,idEstablecimiento);
        if(buscado == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(buscado, HttpStatus.OK);

    }


    @PostMapping("/")
    public ResponseEntity<RegistroProductoEstablecimiento> guardarRegistroProductoEstablecimiento(@RequestBody RegistroProductoEstablecimiento registroProdEst){
        if(registroProdEst == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RegistroProductoEstablecimiento reg = registroProductoEstablecimientoService.crearRegistroProductoEstablecimeinto(registroProdEst);
        return new ResponseEntity<>(reg, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idProducto}/{idEstablecimiento}")
    public ResponseEntity<Void> eliminarRegistroProductoEstablecimiento(@PathVariable long idProducto,@PathVariable long idEstablecimiento){
        registroProductoEstablecimientoService.eliminarRegistroProductoEstablecimiento(idProducto,idEstablecimiento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
