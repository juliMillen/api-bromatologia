package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Rol;
import com.bromatologia.backend.Service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rol/")
public class RolController {

    @Autowired
    private RolService rolService;


    @GetMapping("/{tipoRol}")
    public ResponseEntity<Rol> obtenerRolPorTipo(@PathVariable String tipoRol) {
        Rol rol = rolService.obtenerRolPorTipo(tipoRol);
        return ResponseEntity.ok(rol);
    }

}
