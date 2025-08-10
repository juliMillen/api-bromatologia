package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.EmpresaDTO;
import com.bromatologia.backend.DTO.EmpresaUpdateDTO;

import com.bromatologia.backend.Entity.Empresa;

import com.bromatologia.backend.Service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresa")
//@CrossOrigin("http://localhost:4200/")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/")
    public ResponseEntity<List<EmpresaDTO>> obtenerEmpresas(){
        List<Empresa> empresas = empresaService.obtenerEmpresas();
        List<EmpresaDTO> listaDTO = empresas.stream()
                .map(this::convertirAEmpresaDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/cuit/{cuitEmpresa}")
    public ResponseEntity<EmpresaDTO> obtenerEmpresaPorId(@PathVariable Long cuitEmpresa) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(cuitEmpresa);
        EmpresaDTO empresaDTO = convertirAEmpresaDTO(empresa);
        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
    }

    @GetMapping("/razonSocial/{razonSocial}")
    public ResponseEntity<EmpresaDTO> obtenerEmpresaPorRazonSocial(@PathVariable String razonSocial) {
        Empresa empresa = empresaService.obtenerEmpresaPorRazonSocial(razonSocial);
        EmpresaDTO empresaDTO = convertirAEmpresaDTO(empresa);
        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
    }

    @GetMapping("/propiedades/{cuit}/{razonSocial}/{departamento}")
    public ResponseEntity<EmpresaDTO> obtenerEmpresaPorPropiedades(@PathVariable long cuit, @PathVariable String razonSocial, @PathVariable String departamento) {
        Empresa empresa = empresaService.obtenerEmpresaPorPropiedades(cuit,razonSocial,departamento);
        EmpresaDTO empresaDTO = convertirAEmpresaDTO(empresa);
        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<EmpresaDTO> crearEmpresa(@RequestBody @Valid EmpresaDTO dto) {
        Empresa empresa = convertirADominio(dto);
        Empresa guardada = empresaService.crearEmpresa(empresa);
        EmpresaDTO respuesta = convertirAEmpresaDTO(guardada);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{cuitEmpresa}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable long cuitEmpresa, @RequestBody @Valid EmpresaUpdateDTO dto) {
        Empresa empresaActualizada = empresaService.actualizarEmpresa(cuitEmpresa,dto);
        return new ResponseEntity<>(empresaActualizada, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cuitEmpresa}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable long cuitEmpresa) {
        empresaService.eliminarEmpresaPorId(cuitEmpresa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private EmpresaDTO convertirAEmpresaDTO(Empresa entidad){
        EmpresaDTO dto = new EmpresaDTO();
        dto.setCuitEmpresa(entidad.getCuitEmpresa());
        dto.setRazonSocial(entidad.getRazonSocial());
        dto.setFechaAlta(entidad.getFechaAlta());
        dto.setEmail(entidad.getEmail());
        dto.setTelefono(entidad.getTelefono());
        dto.setDepartamento(entidad.getDepartamento());
        dto.setLocalidad(entidad.getLocalidad());
        dto.setDireccion(entidad.getDireccion());
        dto.setPassword(entidad.getPassword());
        return dto;
    }

    private Empresa convertirADominio(EmpresaDTO dto){

        Empresa entidad = new Empresa();
        entidad.setCuitEmpresa(dto.getCuitEmpresa());
        entidad.setRazonSocial(dto.getRazonSocial());
        entidad.setFechaAlta(dto.getFechaAlta());
        entidad.setEmail(dto.getEmail());
        entidad.setTelefono(dto.getTelefono());
        entidad.setDepartamento(dto.getDepartamento());
        entidad.setLocalidad(dto.getLocalidad());
        entidad.setDireccion(dto.getDireccion());
        entidad.setPassword(dto.getPassword());
        return entidad;
    }

}
