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
@RequestMapping("/api/registro-producto-establecimiento")
public class RegistroProductoEstablecimientoController {
    @Autowired
    private RegistroProductoEstablecimientoService registroProductoEstablecimientoService;


    @GetMapping("/")
    public ResponseEntity<List<RegistroProductoEstablecimientoDTO>> obtenerRegistroProductoEstablecimiento() {
       List<RegistroProductoEstablecimiento> listaRegistrosEnt = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimiento();
       if(listaRegistrosEnt == null || listaRegistrosEnt.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       List<RegistroProductoEstablecimientoDTO> dtos = listaRegistrosEnt.stream()
               .map(this::convertirARegistroDTO)
               .collect(Collectors.toList());
       return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{idProducto}/{idEstablecimiento}")
    public ResponseEntity<RegistroProductoEstablecimientoDTO> obtenerRegistroProductoEstablecimientoPorId(@PathVariable long idProducto,@PathVariable long idEstablecimiento) {
        RegistroProductoEstablecimiento buscado = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimientoPorId(idProducto,idEstablecimiento);
        if(buscado == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertirARegistroDTO(buscado), HttpStatus.OK);

    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegProducto}/{idRegEstablecimiento}")
    public ResponseEntity<RegistroProductoEstablecimientoDTO> guardarRegistroProductoEstablecimiento( @PathVariable long idRegProducto,@PathVariable long idRegEstablecimiento, @RequestBody @Valid RegistroProductoEstablecimientoDTO dto) {
        RegistroProductoEstablecimiento registro = convertirADominio(dto);
        RegistroProductoEstablecimiento regGuardado = registroProductoEstablecimientoService.crearRegistroProductoEstablecimiento(idRegProducto,idRegEstablecimiento,registro);
        RegistroProductoEstablecimientoDTO regGuardadoDTO = convertirARegistroDTO(regGuardado);
        return new ResponseEntity<>(regGuardadoDTO, HttpStatus.CREATED);
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
        dto.setIdRegistroProducto(entidad.getId().getRegistroProductoId());
        dto.setIdRegistroEstablecimiento(entidad.getId().getRegistroEstablecimientoId());
        dto.setRnpaActual(entidad.getRnpaActual());
        dto.setFechaDeEmision(entidad.getFechaDeEmision());
        dto.setRnpaAnterior(entidad.getRnpaAnterior());
        dto.setTipo(entidad.getTipo());
        dto.setNroRne(entidad.getNroRne());
        dto.setCertificado(entidad.getCertificado());
        dto.setExpediente(entidad.getExpediente());
        return dto;
    }

    private RegistroProductoEstablecimiento convertirADominio(RegistroProductoEstablecimientoDTO dto){
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId();
        id.setRegistroProductoId(dto.getIdRegistroProducto());
        id.setRegistroEstablecimientoId(dto.getIdRegistroEstablecimiento());

        RegistroProductoEstablecimiento entidad = new RegistroProductoEstablecimiento();
        entidad.setId(id);
        entidad.setRnpaActual(dto.getRnpaActual());
        entidad.setFechaDeEmision(dto.getFechaDeEmision());
        entidad.setRnpaAnterior(dto.getRnpaAnterior());
        entidad.setTipo(dto.getTipo());
        entidad.setNroRne(dto.getNroRne());
        entidad.setCertificado(dto.getCertificado());
        entidad.setExpediente(dto.getExpediente());
        return entidad;
    }
}
