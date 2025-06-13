package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
@CrossOrigin("http://localhost:4200/")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/")
    public ResponseEntity<List<Empresa>> obtenerEmpresas(){
        List<Empresa> empresas = empresaService.obtenerEmpresas();
        if(empresas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable long id) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(id);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.crearEmpresa(empresa);
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Empresa> actualizarEmpresa(@RequestBody Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.actualizarEmpresa(empresa);
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> eliminarEmpresa(@PathVariable long id) {
        empresaService.eliminarEmpresaPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
