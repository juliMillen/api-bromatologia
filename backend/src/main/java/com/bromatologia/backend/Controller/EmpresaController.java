package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{cuit_Empresa}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable Long cuit_Empresa) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(cuit_Empresa);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @GetMapping("/{cuit}/establecimientos")
    public ResponseEntity<List<Establecimiento>> obtenerEstablecimientosDeEmpresa(@PathVariable Long cuit) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(cuit);
        return new ResponseEntity<>(empresa.getEstablecimientos(), HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody @Valid Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.crearEmpresa(empresa);
        System.out.println("Empresa creada correctamente");
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{cuit_Empresa}")
    public ResponseEntity<Establecimiento> agregarEstablecimiento(@PathVariable long cuit_Empresa, @RequestBody @Valid Establecimiento establecimiento){
        Establecimiento creado = empresaService.agregarEstablecimiento(cuit_Empresa, establecimiento);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{cuit_Empresa}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable long cuit_Empresa,@RequestBody @Valid Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.actualizarEmpresa(cuit_Empresa,empresa);
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cuit_Empresa}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable long cuit_Empresa) {
        empresaService.eliminarEmpresaPorId(cuit_Empresa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
