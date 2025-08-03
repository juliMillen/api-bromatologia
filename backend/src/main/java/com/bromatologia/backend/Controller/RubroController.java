package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.ActividadDTO;
import com.bromatologia.backend.DTO.CategoriaDTO;
import com.bromatologia.backend.DTO.RubroDTO;
import com.bromatologia.backend.Entity.Actividad;
import com.bromatologia.backend.Entity.Categoria;
import com.bromatologia.backend.Entity.Rubro;
import com.bromatologia.backend.Service.RubroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rubro")
public class RubroController {

    @Autowired
    private RubroService rubroService;

    @GetMapping()
    public ResponseEntity<List<RubroDTO>> listarRubro(){
        List<Rubro> listaRubros = rubroService.obtenerRubros();
        List<RubroDTO> listaDTO = listaRubros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RubroDTO> obtenerRubroPorId(@PathVariable long id){
        Rubro buscado = rubroService.obtenerRubrosPorIdRubro(id);
        RubroDTO dto = convertirADTO(buscado);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<RubroDTO> registrarRubro(@RequestBody RubroDTO dto){
        Rubro rubro = convertirADominio(dto);
        Rubro guardado = rubroService.crearRubro(rubro);
        RubroDTO rubroDTO = convertirADTO(guardado);
        return new ResponseEntity<>(rubroDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idRubro}/categoria/{idCategoria}")
    public ResponseEntity<Categoria> asignarCategoria(@PathVariable long idRubro,@PathVariable long idCategoria){
        Categoria nueva = rubroService.asignarCategoria(idRubro,idCategoria);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRubro(@PathVariable long id){
        rubroService.eliminarRubro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private RubroDTO convertirADTO(Rubro entidad){
        RubroDTO dto = new RubroDTO();
        dto.setIdRubro(entidad.getIdRubro());
        dto.setNombreRubro(entidad.getNombreRubro());


        //categorias

        List<CategoriaDTO> listaDTO = entidad.getListaCategorias()
                .stream()
                .map(e ->{
                    CategoriaDTO cat = new CategoriaDTO();
                    cat.setIdCategoria(e.getIdCategoria());
                    cat.setNombre(e.getNombreCategoria());
                    return cat;
                }).toList();
        dto.setListaDTO(listaDTO);
        return dto;
    }

    private Rubro convertirADominio(RubroDTO dto){

        Rubro entidad = new Rubro();
        entidad.setIdRubro(dto.getIdRubro());
        entidad.setNombreRubro(dto.getNombreRubro());


        //Categorias

        List<Categoria> listaCat = dto.getListaDTO()
                .stream()
                .map(e->{
                    Categoria cat = new Categoria();
                    cat.setIdCategoria(e.getIdCategoria());
                    cat.setNombreCategoria(e.getNombre());
                    return cat;
                }).toList();
        entidad.setListaCategorias(listaCat);
        return entidad;
    }
}
