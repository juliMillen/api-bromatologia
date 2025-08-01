package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.MantenimientoDTO;
import com.bromatologia.backend.DTO.ReciboDTO;
import com.bromatologia.backend.DTO.TramiteDTO;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Service.MantenimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mantenimiento")
//@CrossOrigin("http://localhost:4200/")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping("/")
    public ResponseEntity<List<MantenimientoDTO>>obtenerMantenimientos(){
        List<Mantenimiento> listaMantenimientos = mantenimientoService.obtenerMantenimientos();
        if(listaMantenimientos == null ||listaMantenimientos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<MantenimientoDTO> listaDTO = listaMantenimientos
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> obtenerMantenimientoPorId(@PathVariable long id){
        Mantenimiento encontrado = mantenimientoService.obtenerMantenimiento(id);
        MantenimientoDTO respuesta = convertirADTO(encontrado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}/tramites")
    public ResponseEntity<List<TramiteDTO>> obtenerTramitesPorMantenimiento(@PathVariable long id) {
        Mantenimiento buscado = mantenimientoService.obtenerMantenimiento(id);
        List<TramiteDTO> tramites = buscado.getTramites().stream().map(t -> {
            TramiteDTO tramite = new TramiteDTO();
            tramite.setIdTramite(t.getIdTramite());
            tramite.setNombreTramite(t.getNombreTramite());

            if(t.getRecibo() != null){
                ReciboDTO reciboDTO = new ReciboDTO();
                reciboDTO.setFechaRecibo(t.getRecibo().getFechaRecibo());
                reciboDTO.setImporte(t.getRecibo().getImporte());
            }

            return tramite;
        }).toList();
        return new ResponseEntity<>(tramites, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<MantenimientoDTO> registrarMantenimiento(@RequestBody @Valid MantenimientoDTO mantenimiento){
        Mantenimiento nuevo = mantenimientoService.registrarMantenimiento(convertirADominio(mantenimiento));
        MantenimientoDTO respuesta = convertirADTO(nuevo);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idMantenimiento}/tramite/{idTramite}")
    public ResponseEntity<TramiteDTO> agregarTramite(@PathVariable long idMantenimiento, @PathVariable long idTramite){
        Tramite nuevo = mantenimientoService.agregarTramite(idMantenimiento, idTramite);
        TramiteDTO dto = convertirATramiteDTO(nuevo);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mantenimiento> eliminarMantenimiento(@PathVariable long id){
        mantenimientoService.eliminarMantenimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //metodos de mapeo DTO <---> entidad
    private MantenimientoDTO convertirADTO(Mantenimiento entidad) {
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

    private Mantenimiento convertirADominio(MantenimientoDTO dto) {
        Mantenimiento entidad = new Mantenimiento();
        entidad.setIdMantenimiento(dto.getIdMantenimiento());
        entidad.setFechaMantenimiento(dto.getFechaMantenimiento());
        entidad.setEnlaceRecibido(dto.getEnlaceRecibido());

        //Tramites

        List<Tramite> tramites = dto.getTramites()
                .stream()
                .map(e ->{
                    Tramite tramite = new Tramite();
                    tramite.setIdTramite(e.getIdTramite());
                    tramite.setNombreTramite(e.getNombreTramite());

                    Recibo recibo = new Recibo();
                    recibo.setFechaRecibo(e.getRecibo().getFechaRecibo());
                    recibo.setImporte(e.getRecibo().getImporte());

                    tramite.setRecibo(recibo);
                    return tramite;
                }).toList();
        entidad.setTramites(tramites);
        return entidad;
    }


    //tramite
    //metodos de mapeo DTO <---> entidad
    private TramiteDTO convertirATramiteDTO(Tramite entidad) {
        TramiteDTO dto = new TramiteDTO();
        dto.setIdTramite(entidad.getIdTramite());
        dto.setNombreTramite(entidad.getNombreTramite());

        //Recibo
        ReciboDTO recibo = new ReciboDTO();
        recibo.setIdRecibo(entidad.getRecibo().getIdRecibo());
        recibo.setFechaRecibo(entidad.getRecibo().getFechaRecibo());
        recibo.setImporte(entidad.getRecibo().getImporte());
        dto.setRecibo(recibo);
        return dto;
    }
}
