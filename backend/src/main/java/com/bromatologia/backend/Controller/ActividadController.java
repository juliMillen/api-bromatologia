package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.ActividadDTO;
import com.bromatologia.backend.DTO.CategoriaDTO;
import com.bromatologia.backend.Entity.Actividad;
import com.bromatologia.backend.Entity.Categoria;
import com.bromatologia.backend.Service.ActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/actividad")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping()
    public ResponseEntity<List<ActividadDTO>> obtenerActividades(){
       List<Actividad> listaActividades = actividadService.obtenerActividades();
       List<ActividadDTO> listaDTO = listaActividades.stream()
               .map(this::convertirADTO)
               .collect(Collectors.toList());
       return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadDTO> obtenerActividad(@PathVariable long id){
        Actividad actividad = actividadService.obtenerActividad(id);
        ActividadDTO actividadDTO = convertirADTO(actividad);
        return new ResponseEntity<>(actividadDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ActividadDTO> crearActividad(@RequestBody @Valid ActividadDTO dto){
        Actividad actividad = convertirADominio(dto);
        Actividad guardada = actividadService.crearActividad(actividad);
        ActividadDTO actividadDTO = convertirADTO(guardada);
        return new ResponseEntity<>(actividadDTO, HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ActividadDTO> eliminarActividad(@PathVariable long id){
        actividadService.eliminarActividad(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




    //metodos de mapeo DTO <---> entidad
    private ActividadDTO convertirADTO(Actividad entidad){
        ActividadDTO dto = new ActividadDTO();
        dto.setIdActividad(entidad.getIdActividad());
        dto.setNombreActividad(entidad.getNombreActividad());
        return dto;
    }

    private Actividad convertirADominio(ActividadDTO dto){

        Actividad entidad = new Actividad();
        entidad.setIdActividad(dto.getIdActividad());
        entidad.setNombreActividad(dto.getNombreActividad());
        return entidad;
    }
}
