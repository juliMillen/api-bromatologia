package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.TitularDTO;
import com.bromatologia.backend.DTO.TitularUpdateDTO;
import com.bromatologia.backend.Entity.Titular;
import com.bromatologia.backend.Service.TitularService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/titular")
//@CrossOrigin("http://localhost:4200/")
public class TitularController {
    @Autowired
    private TitularService titularService;

    @GetMapping("/")
    public ResponseEntity<List<TitularDTO>> obtenerTitulares() {
        List<Titular> listaTitulares = titularService.obtenerTitular();
        if(listaTitulares == null || listaTitulares.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<TitularDTO> listaDTO = listaTitulares
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{cuitTitular}")
    public ResponseEntity<TitularDTO> obtenerTitular(@PathVariable long cuitTitular) {
        Titular buscado = titularService.obtenerTitularPorCuit(cuitTitular);
        TitularDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<TitularDTO> crearTitular(@RequestBody @Valid TitularDTO titular) {
        Titular nuevoTitular = convertirADominio(titular);
        Titular guardado = titularService.crearTitular(nuevoTitular);
        TitularDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{cuitTitular}")
    public ResponseEntity<Titular> actualizarTitular(@PathVariable long cuitTitular,@RequestBody @Valid TitularUpdateDTO dto) {

        Titular nuevoTitular = titularService.actualizarTitular(cuitTitular,dto);
        return new ResponseEntity<>(nuevoTitular, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cuitTitular}")
    public ResponseEntity<Void> eliminarTitular(@PathVariable long cuitTitular) {
        titularService.eliminarTitular(cuitTitular);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //metodos de mapeo DTO <---> entidad
    private TitularDTO convertirADTO(Titular entidad) {
        TitularDTO dto = new TitularDTO();
        dto.setCuitTitular(entidad.getCuitTitular());
        dto.setNombreTitular(entidad.getNombreTitular());
        dto.setEmail(entidad.getEmail());
        dto.setTelefono(entidad.getTelefono());
        return dto;
    }

    private Titular convertirADominio(TitularDTO dto) {
        Titular entidad = new Titular();
        entidad.setCuitTitular(dto.getCuitTitular());
        entidad.setNombreTitular(dto.getNombreTitular());
        entidad.setEmail(dto.getEmail());
        entidad.setTelefono(dto.getTelefono());
        return entidad;
    }
}
