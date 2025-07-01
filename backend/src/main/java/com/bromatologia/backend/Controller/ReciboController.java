package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.ReciboDTO;
import com.bromatologia.backend.Entity.Recibo;
import com.bromatologia.backend.Service.ReciboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recibo")
//@CrossOrigin("http://localhost:4200/")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;


    @GetMapping("/")
    public ResponseEntity<List<ReciboDTO>> obtenerRecibos() {
        List<Recibo> listaRecibos = reciboService.obtenerRecibos();
        if(listaRecibos == null || listaRecibos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ReciboDTO> listaDTO = listaRecibos
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReciboDTO> obtenerReciboPorId(@PathVariable long id) {
        Recibo buscado = reciboService.obtenerReciboPorId(id);
        ReciboDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<ReciboDTO> agregarRecibo(@RequestBody @Valid ReciboDTO recibo) {

        Recibo nuevoRecibo = convertirADominio(recibo);
        Recibo guardado = reciboService.crearRecibo(nuevoRecibo);
        ReciboDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Recibo> eliminarRecibo(@PathVariable long id) {
        reciboService.eliminarReciboPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //metodos de mapeo DTO <---> entidad
    private ReciboDTO convertirADTO(Recibo entidad) {
        ReciboDTO dto = new ReciboDTO();
        dto.setIdRecibo(entidad.getIdRecibo());
        dto.setFechaRecibo(entidad.getFechaRecibo());
        dto.setImporte(entidad.getImporte());
        return dto;
    }

    private Recibo convertirADominio(ReciboDTO dto) {
        Recibo entidad = new Recibo();
        entidad.setIdRecibo(dto.getIdRecibo());
        entidad.setFechaRecibo(dto.getFechaRecibo());
        entidad.setImporte(dto.getImporte());
        return entidad;
    }
}
