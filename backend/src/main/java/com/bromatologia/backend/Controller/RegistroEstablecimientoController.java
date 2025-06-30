package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.*;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Service.RegistroEstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registroEstablecimiento")
//@CrossOrigin("http://localhost:4200/")
public class RegistroEstablecimientoController {
    @Autowired
    private RegistroEstablecimientoService registroEstablecimientoService;


    @GetMapping("/")
    public ResponseEntity<List<RegistroEstablecimiento>> getRegistroEstablecimiento() {
        List<RegistroEstablecimiento> listaRegistrosEst = registroEstablecimientoService.obtenerEstablecimientos();
        return new ResponseEntity<>(listaRegistrosEst, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroEstablecimientoDTO>getRegistroEstablecimientoById(@PathVariable long id) {
        RegistroEstablecimiento buscado = registroEstablecimientoService.obtenerEstablecimientoById(id);
        if(buscado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RegistroEstablecimientoDTO dto = convertirADTO(buscado);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<RegistroEstablecimientoDTO> guardarRegistro(@RequestBody @Valid RegistroEstablecimientoDTO dto) {
        RegistroEstablecimiento nuevoRegistro = convertirADominio(dto);
        RegistroEstablecimiento guardado = registroEstablecimientoService.guardarRegistro(nuevoRegistro);
        RegistroEstablecimientoDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegistroEstablecimiento}/empresa/{cuitEmpresa}")
    public ResponseEntity<Empresa> asignarEmpresa(@PathVariable long idRegistroEstablecimiento, @PathVariable long cuitEmpresa) {
        Empresa nueva = registroEstablecimientoService.asignarEmpresa(idRegistroEstablecimiento,cuitEmpresa);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegistroEstablecimiento}/titular/{idTitular}")
    public ResponseEntity<Titular> asignarTitular(@PathVariable long idRegistroEstablecimiento, @PathVariable long idTitular) {
        Titular nuevo = registroEstablecimientoService.asignarTitular(idRegistroEstablecimiento,idTitular);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegistroEstablecimiento}/establecimiento/{idEstablecimiento}")
    public ResponseEntity<Establecimiento> asignarEstablecimiento(@PathVariable long idRegistroEstablecimiento, @PathVariable long idEstablecimiento) {
        Establecimiento nuevo = registroEstablecimientoService.asignarEstablecimiento(idRegistroEstablecimiento,idEstablecimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("{idRegistroEstablecimiento}/mantenimiento/{idMantenimiento}")
    public ResponseEntity<Mantenimiento> agregarMantenimiento(@PathVariable long idRegistroEstablecimiento, @PathVariable long idMantenimiento) {
        Mantenimiento nuevo = registroEstablecimientoService.agregarMantenimiento(idRegistroEstablecimiento, idMantenimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroEstablecimiento> eliminarRegistro(@PathVariable long id) {
        registroEstablecimientoService.eliminarRegistro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private RegistroEstablecimientoDTO convertirADTO(RegistroEstablecimiento entidad){
        RegistroEstablecimientoDTO dto = new RegistroEstablecimientoDTO();
        dto.setIdRegistroEstablecimiento(entidad.getIdRegistroEstablecimiento());
        dto.setIdEstablecimiento(entidad.getEstablecimiento().getIdEstablecimiento());
        dto.setCuitTitular(entidad.getTitular().getCuitTitular());
        dto.setArancel(entidad.getArancel());
        dto.setCategoriaAnt(entidad.getCategoriaAnt());
        dto.setFechaEmision(entidad.getFechaEmision());
        dto.setFechaVencimiento(entidad.getFechaVencimiento());
        dto.setEstado(entidad.getEstado());


        //Empresa
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setCuitEmpresa(entidad.getEmpresa().getCuitEmpresa());

        //titular
        TitularDTO titularDTO = new TitularDTO();
        titularDTO.setCuitTitular(entidad.getTitular().getCuitTitular());

        //Establecimiento
        EstablecimientoDTO establecimientoDTO = new EstablecimientoDTO();
        establecimientoDTO.setIdEstablecimiento(entidad.getEstablecimiento().getIdEstablecimiento());

        //Mantenimiento
        List<MantenimientoDTO> mantenimientosDTO = entidad.getMantenimientos()
                .stream()
                .map(e->{
                    MantenimientoDTO mant = new MantenimientoDTO();
                    mant.setIdMantenimiento(e.getIdMantenimiento());
                    mant.setFechaMantenimiento(e.getFechaMantenimiento());
                    mant.setEnlaceRecibido(e.getEnlaceRecibido());
                    return mant;
                }).toList();
        dto.setMantenimientos(mantenimientosDTO);
        return dto;
    }


    private RegistroEstablecimiento convertirADominio(RegistroEstablecimientoDTO dto) {
        RegistroEstablecimiento entidad = new RegistroEstablecimiento();
        entidad.setIdRegistroEstablecimiento(dto.getIdRegistroEstablecimiento());
        entidad.setCategoriaAnt(dto.getCategoriaAnt());
        entidad.setArancel(dto.getArancel());
        entidad.setFechaEmision(dto.getFechaEmision());
        entidad.setFechaVencimiento(dto.getFechaVencimiento());
        entidad.setEstado(dto.getEstado());


        //Empresa
        Empresa empresa = new Empresa();
        empresa.setCuitEmpresa(dto.getCuitEmpresa());
        entidad.setEmpresa(empresa);

        //Titular
        Titular titular = new Titular();
        titular.setCuitTitular(dto.getCuitTitular());
        entidad.setTitular(titular);


        //Establecimiento
        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setIdEstablecimiento(dto.getIdEstablecimiento());
        entidad.setEstablecimiento(establecimiento);

        //mantenimiento
        List<Mantenimiento> mantenimientos = dto.getMantenimientos()
                .stream()
                .map(e->{
                    Mantenimiento mant = new Mantenimiento();
                    mant.setIdMantenimiento(e.getIdMantenimiento());
                    mant.setFechaMantenimiento(e.getFechaMantenimiento());
                    mant.setEnlaceRecibido(e.getEnlaceRecibido());
                    return mant;
                }).toList();
        entidad.setMantenimientos(mantenimientos);
        return entidad;
    }
}
