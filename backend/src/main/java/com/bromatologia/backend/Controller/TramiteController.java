package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.ReciboDTO;
import com.bromatologia.backend.DTO.TramiteDTO;
import com.bromatologia.backend.Entity.Recibo;
import com.bromatologia.backend.Entity.Tramite;
import com.bromatologia.backend.Service.TramiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tramite")
//@CrossOrigin("http://localhost:4200/")
public class TramiteController {
    @Autowired
    private TramiteService tramiteService;


    @GetMapping("/")
    public ResponseEntity<List<Tramite>> obtenerTramites() {
        List<Tramite> listaTramites = tramiteService.obtenerTramites();
        return new ResponseEntity<>(listaTramites, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramiteDTO> obtenerTramite(@PathVariable long id) {
        Tramite buscado = tramiteService.obtenerTramitePorId(id);
        TramiteDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<TramiteDTO> crearTramite(@RequestBody @Valid TramiteDTO tramite) {
        Tramite nuevoTramite = tramiteService.crearTramite(convertirADominio(tramite));
        Tramite guardado = tramiteService.crearTramite(nuevoTramite);
        TramiteDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Tramite> eliminarTramite(@PathVariable long id) {
        tramiteService.eliminarTramite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //metodos de mapeo DTO <---> entidad
    private TramiteDTO convertirADTO(Tramite entidad) {
        TramiteDTO dto = new TramiteDTO();
        dto.setIdTramite(entidad.getIdTramite());
        dto.setNombreTramite(entidad.getNombreTramite());

        //Recibo
        ReciboDTO recibo = new ReciboDTO();
        recibo.setIdRecibo(entidad.getRecibo().getIdRecibo());
        recibo.setFechaRecibo(entidad.getRecibo().getFechaRecibo());
        recibo.setImporte(entidad.getRecibo().getImporte());
        return dto;
    }

    private Tramite convertirADominio(TramiteDTO dto) {
        Tramite entidad = new Tramite();
        entidad.setIdTramite(dto.getIdTramite());
        entidad.setNombreTramite(dto.getNombreTramite());

        //Recibo
        Recibo recibo = new Recibo();
        recibo.setIdRecibo(dto.getRecibo().getIdRecibo());
        recibo.setFechaRecibo(dto.getRecibo().getFechaRecibo());
        recibo.setImporte(dto.getRecibo().getImporte());
        entidad.setRecibo(recibo);
        return entidad;
    }
}
