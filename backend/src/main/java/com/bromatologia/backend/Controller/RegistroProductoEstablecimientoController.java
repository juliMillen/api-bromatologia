package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.RegistroProductoEstablecimientoDTO;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimientoId;
import com.bromatologia.backend.Service.RegistroProductoEstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/registro-producto-establecimiento")
public class RegistroProductoEstablecimientoController {
    @Autowired
    private RegistroProductoEstablecimientoService registroProductoEstablecimientoService;


    @GetMapping("/")
    public ResponseEntity<List<RegistroProductoEstablecimientoDTO>> obtenerRegistroProductoEstablecimiento() {
       List<RegistroProductoEstablecimiento> listaRegistrosEnt = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimiento();
       List<RegistroProductoEstablecimientoDTO> dtos = listaRegistrosEnt.stream()
               .map(this::convertirARegistroDTO)
               .collect(Collectors.toList());
       return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{idProducto}/{idEstablecimiento}")
    public ResponseEntity<RegistroProductoEstablecimientoDTO> obtenerRegistroProductoEstablecimientoPorId(@PathVariable long idProducto,@PathVariable long idEstablecimiento){
        RegistroProductoEstablecimiento buscado = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimientoPorId(idProducto,idEstablecimiento);
        if(buscado == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertirARegistroDTO(buscado), HttpStatus.OK);

    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<RegistroProductoEstablecimientoDTO> guardarRegistroProductoEstablecimiento( @Valid @RequestBody RegistroProductoEstablecimientoDTO registroProdEst){
        if(registroProdEst == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RegistroProductoEstablecimiento regEntidad = convertirADominio(registroProdEst);
        RegistroProductoEstablecimiento regGuardado = registroProductoEstablecimientoService
                .crearRegistroProductoEstablecimiento(regEntidad);
        return new ResponseEntity<>(convertirARegistroDTO(regGuardado), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idProducto}/{idEstablecimiento}")
    public ResponseEntity<Void> eliminarRegistroProductoEstablecimiento(@PathVariable long idProducto,@PathVariable long idEstablecimiento){
        registroProductoEstablecimientoService.eliminarRegistroProductoEstablecimiento(idProducto,idEstablecimiento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private RegistroProductoEstablecimientoDTO convertirARegistroDTO(RegistroProductoEstablecimiento entidad){
        RegistroProductoEstablecimientoDTO dto = new RegistroProductoEstablecimientoDTO();
        dto.setIdProducto(entidad.getId().getRegistroProductoId());
        dto.setIdEstablecimiento(entidad.getId().getRegistroEstablecimientoId());
        dto.setNroRnpaActual(entidad.getNroRnpaActual());
        dto.setFechaDeEmision(entidad.getFechaDeEmision());
        dto.setNroAnteriorRnpa(entidad.getNroAnteriorRnpa());
        dto.setTipo(entidad.getTipo());
        dto.setNroRne(entidad.getNroRne());
        dto.setCertificado(entidad.getCertificado());
        dto.setExpediente(entidad.getExpediente());
        return dto;
    }

    private RegistroProductoEstablecimiento convertirADominio(RegistroProductoEstablecimientoDTO dto){
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId();
        id.setRegistroProductoId(dto.getIdProducto());
        id.setRegistroEstablecimientoId(dto.getIdEstablecimiento());

        RegistroProductoEstablecimiento entidad = new RegistroProductoEstablecimiento();
        entidad.setId(id);
        entidad.setNroRnpaActual(dto.getNroRnpaActual());
        entidad.setFechaDeEmision(dto.getFechaDeEmision());
        entidad.setNroAnteriorRnpa(dto.getNroAnteriorRnpa());
        entidad.setTipo(dto.getTipo());
        entidad.setNroRne(dto.getNroRne());
        entidad.setCertificado(dto.getCertificado());
        entidad.setExpediente(dto.getExpediente());
        return entidad;
    }
}
