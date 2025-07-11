package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.EmpresaDTO;
import com.bromatologia.backend.DTO.EmpresaUpdateDTO;
import com.bromatologia.backend.DTO.EstablecimientoDTO;
import com.bromatologia.backend.DTO.TitularDTO;
import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Titular;
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

    @GetMapping("/{cuitEmpresa}")
    public ResponseEntity<EmpresaDTO> obtenerEmpresaPorId(@PathVariable Long cuitEmpresa) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(cuitEmpresa);
        EmpresaDTO empresaDTO = convertirAEmpresaDTO(empresa);
        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
    }

    @GetMapping("/{cuit}/establecimientos")
    public ResponseEntity<List<EstablecimientoDTO>> obtenerEstablecimientosDeEmpresa(@PathVariable Long cuit) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(cuit);

        List<EstablecimientoDTO> listaDTO = empresa.getEstablecimientos()
                .stream()
                .map(this::convertirAEstablecimientoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<EmpresaDTO> crearEmpresa(@RequestBody @Valid EmpresaDTO dto) {
        Empresa empresa = convertirADominio(dto);
        Empresa guardada = empresaService.crearEmpresa(empresa);
        EmpresaDTO respuesta = convertirAEmpresaDTO(guardada);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{cuitEmpresa}/establecimiento/{idEstablecimiento}")
    public ResponseEntity<Establecimiento> agregarEstablecimiento(@PathVariable long cuitEmpresa, @PathVariable long idEstablecimiento){
        Establecimiento creado = empresaService.agregarEstablecimiento(cuitEmpresa, idEstablecimiento);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
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
        dto.setNombreEmpresa(entidad.getNombreEmpresa());
        dto.setEmail(entidad.getEmail());
        dto.setTelefono(entidad.getTelefono());


        //Titular
        Titular titularEntidad = entidad.getTitular();
        if(titularEntidad != null){
            TitularDTO titularDTO = new TitularDTO();
            titularDTO.setCuitTitular(titularEntidad.getCuitTitular());
            titularDTO.setNombreTitular(titularEntidad.getNombreTitular());
            titularDTO.setEmail(titularEntidad.getEmail());
            titularDTO.setTelefono(titularEntidad.getTelefono());
            dto.setTitular(titularDTO);
        }

        //Establecimiento
        List<EstablecimientoDTO> establecimientosDTO = entidad.getEstablecimientos()
                .stream()
                .map(e ->{
                    EstablecimientoDTO establecimientoDTO = new EstablecimientoDTO();
                    establecimientoDTO.setIdEstablecimiento(e.getIdEstablecimiento());
                    establecimientoDTO.setLocalidad(e.getLocalidad());
                    establecimientoDTO.setDepartamento(e.getDepartamento());
                    establecimientoDTO.setDireccion(e.getDireccion());
                    establecimientoDTO.setCuitEmpresa(e.getEmpresa().getCuitEmpresa());
                    return establecimientoDTO;
                }).toList();
        dto.setEstablecimientos(establecimientosDTO);
        return dto;
    }

    private Empresa convertirADominio(EmpresaDTO dto){

        Empresa entidad = new Empresa();
        entidad.setCuitEmpresa(dto.getCuitEmpresa());
        entidad.setNombreEmpresa(dto.getNombreEmpresa());
        entidad.setEmail(dto.getEmail());
        entidad.setTelefono(dto.getTelefono());

        //titular
        Titular titular = new Titular();
        titular.setCuitTitular(dto.getTitular().getCuitTitular());
        titular.setNombreTitular(dto.getTitular().getNombreTitular());
        titular.setEmail(dto.getTitular().getEmail());
        titular.setTelefono(dto.getTitular().getTelefono());
        entidad.setTitular(titular);

        //establecimiento
        List<Establecimiento> establecimiento = dto.getEstablecimientos()
                .stream()
                .map(e->{
                    Establecimiento est = new Establecimiento();
                    est.setIdEstablecimiento(e.getIdEstablecimiento());
                    est.setLocalidad(e.getLocalidad());
                    est.setDepartamento(e.getDepartamento());
                    est.setDireccion(e.getDireccion());
                    return est;
                }).toList();
        entidad.setEstablecimientos(establecimiento);
        return entidad;
    }

    private EstablecimientoDTO convertirAEstablecimientoDTO(Establecimiento e){
        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setIdEstablecimiento(e.getIdEstablecimiento());
        dto.setDepartamento(e.getDepartamento());
        dto.setLocalidad(e.getLocalidad());
        dto.setDireccion(e.getDireccion());
        dto.setCuitEmpresa(e.getEmpresa().getCuitEmpresa());
        return dto;
    }

}
