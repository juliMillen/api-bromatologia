package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
//@CrossOrigin("http://localhost:4200/")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/")
    public ResponseEntity<List<Empresa>> obtenerEmpresas(){
        List<Empresa> empresas = empresaService.obtenerEmpresas();
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable long id) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(id);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody @Valid Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.crearEmpresa(empresa);
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.CREATED);
    }

    @PutMapping("/{cuit_Empresa}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long cuit_Empresa,@RequestBody @Valid Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.actualizarEmpresa(cuit_Empresa,empresa);
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.OK);
    }

    @DeleteMapping("/{cuit_Empresa}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable long cuit_Empresa) {
        empresaService.eliminarEmpresaPorId(cuit_Empresa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
