package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.*;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Exception.RegistroProductoException;
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
    public ResponseEntity<RegistroProductoDTO> obtenerRegistroProductoById(@PathVariable String id) {
        RegistroProducto buscado = registroProductoService.obtenerRegistroProducto(id);
        RegistroProductoDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/registroProductoConMantenimientos")
    public ResponseEntity<List<RegistroProductoDTO>> obtenerConProductoYMantenimiento(){
        List<RegistroProducto> listaRegistrosConMantenimiento = registroProductoService.obtenerTodosConProductoYMantenimiento();
        if(listaRegistrosConMantenimiento == null || listaRegistrosConMantenimiento.isEmpty()){
            throw new RegistroProductoException("No se han encontrado los registros con mantenimiento");
        }

        List<RegistroProductoDTO> listaDTO = listaRegistrosConMantenimiento
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
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
    public ResponseEntity<MantenimientoDTO> agregarMantenimiento(@PathVariable String idRegistroProducto, @PathVariable long idMantenimiento) {
        Mantenimiento nuevo = registroProductoService.agregarMantenimiento(idRegistroProducto, idMantenimiento);
        MantenimientoDTO dto = convertirAMantenimientoDTO(nuevo);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{rppa}")
    public ResponseEntity<RegistroProductoDTO> actualizarRegistro(@PathVariable String rppa, @RequestBody @Valid RegistroProdUpdateDTO dto) {
        RegistroProducto actualizado = registroProductoService.actualizarRegistroProducto(rppa, dto);
        RegistroProductoDTO dtoActualizado = convertirADTO(actualizado);
        return new ResponseEntity<>(dtoActualizado, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroProducto> eliminarRegistroProducto(@PathVariable String id) {
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
        dto.setElaborador(entidad.getElaborador());

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
        entidad.setElaborador(dto.getElaborador());

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

    //mantenimiento a dto
    //metodos de mapeo DTO <---> entidad
    private MantenimientoDTO convertirAMantenimientoDTO(Mantenimiento entidad) {
        MantenimientoDTO dto = new MantenimientoDTO();
        dto.setIdMantenimiento(entidad.getIdMantenimiento());
        dto.setFechaMantenimiento(entidad.getFechaMantenimiento());
        dto.setEnlaceRecibido(entidad.getEnlaceRecibido());

        //Tramites
        List<TramiteDTO> tramites = entidad.getTramites()
                .stream()
                .map(e->{
                    TramiteDTO tramite = new TramiteDTO();
                    tramite.setIdTramite(e.getIdTramite());
                    tramite.setNombreTramite(e.getNombreTramite());

                    ReciboDTO recibo = new ReciboDTO();
                    recibo.setFechaRecibo(e.getRecibo().getFechaRecibo());
                    recibo.setImporte(e.getRecibo().getImporte());

                    tramite.setRecibo(recibo);
                    return tramite;
                }).toList();
        dto.setTramites(tramites);
        return dto;
    }
}
