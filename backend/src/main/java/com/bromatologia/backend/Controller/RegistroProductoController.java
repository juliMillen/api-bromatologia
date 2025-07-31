package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.*;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Service.RegistroProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registroProducto")
//@CrossOrigin("http://localhost:4200/")
public class RegistroProductoController {
    @Autowired
    private RegistroProductoService registroProductoService;

    @GetMapping("/")
    public ResponseEntity<List<RegistroProductoDTO>> obtenerRegistrosProductos() {
       List<RegistroProducto> listaRegistrosProd = registroProductoService.obtenerRegistrosProducto();
       if(listaRegistrosProd == null || listaRegistrosProd.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }

       List<RegistroProductoDTO> listaDTO = listaRegistrosProd
               .stream()
               .map(this::convertirADTO)
               .collect(Collectors.toList());
       return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroProductoDTO> obtenerRegistroProductoById(@PathVariable long id) {
        RegistroProducto buscado = registroProductoService.obtenerRegistroProducto(id);
        RegistroProductoDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<RegistroProductoDTO> guardarRegistroProducto(@RequestBody  @Valid RegistroProductoDTO dto) {
        RegistroProducto nuevoRegistroProducto = convertirADominio(dto);
        RegistroProducto guardado = registroProductoService.guardarRegistroProducto(nuevoRegistroProducto);
        RegistroProductoDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegistroProducto}/mantenimiento/{idMantenimiento}")
    public ResponseEntity<Mantenimiento> agregarMantenimiento(@PathVariable long idRegistroProducto, @PathVariable long idMantenimiento) {
        Mantenimiento nuevo = registroProductoService.agregarMantenimiento(idRegistroProducto, idMantenimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroProducto> eliminarRegistroProducto(@PathVariable long id) {
        registroProductoService.eliminarRegistroProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private RegistroProductoDTO convertirADTO(RegistroProducto entidad) {
        RegistroProductoDTO dto = new RegistroProductoDTO();
        dto.setRppa(entidad.getRppa());
        dto.setFechaEmision(entidad.getFechaEmision());
        dto.setFechaVencimiento(entidad.getFechaVencimiento());
        dto.setDenominacion(entidad.getDenominacion());
        dto.setMarca(entidad.getMarca());
        dto.setNombreFantasia(entidad.getNombreFantasia());
        dto.setCategoriaProducto(entidad.getCategoriaProducto());
        dto.setExpediente(entidad.getExpediente());
        dto.setEnlace(entidad.getEnlace());

       RegistroEstablecimientoDTO regDto = new RegistroEstablecimientoDTO();
       regDto.setRpe(entidad.getRegistroEstablecimiento().getRpe());
       dto.setRegistroEstablecimiento(regDto);



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

    private RegistroProducto convertirADominio(RegistroProductoDTO dto) {
        RegistroProducto entidad = new RegistroProducto();
        entidad.setRppa(dto.getRppa());
        entidad.setFechaEmision(dto.getFechaEmision());
        entidad.setFechaVencimiento(dto.getFechaVencimiento());
        entidad.setDenominacion(dto.getDenominacion());
        entidad.setMarca(dto.getMarca());
        entidad.setNombreFantasia(dto.getNombreFantasia());
        entidad.setCategoriaProducto(dto.getCategoriaProducto());
        entidad.setExpediente(dto.getExpediente());
        entidad.setEnlace(dto.getEnlace());

        RegistroEstablecimiento registroEstablecimiento = new RegistroEstablecimiento();
        registroEstablecimiento.setRpe(dto.getRegistroEstablecimiento().getRpe());
        entidad.setRegistroEstablecimiento(registroEstablecimiento);

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
