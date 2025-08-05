package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.ActividadDTO;
import com.bromatologia.backend.DTO.CategoriaDTO;
import com.bromatologia.backend.DTO.EmpresaDTO;
import com.bromatologia.backend.Entity.Actividad;
import com.bromatologia.backend.Entity.Categoria;
import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> listarCategorias(){
       List<Categoria> listaCategorias = categoriaService.obtenerCategorias();
       List<CategoriaDTO> listaDTO = listaCategorias.stream()
               .map(this::convertirACategoriaDTO)
               .collect(Collectors.toList());
       return new ResponseEntity<>(listaDTO,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(@PathVariable Long id){
        Categoria buscada = categoriaService.obtenerCategoriaPorId(id);
        CategoriaDTO categoriaDTO = convertirACategoriaDTO(buscada);
        return new ResponseEntity<>(categoriaDTO,HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombreCategoria}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorNombre(@PathVariable String nombreCategoria){
        Categoria buscada = categoriaService.obtenerCategoriaPorNombre(nombreCategoria);
        CategoriaDTO categoriaDTO = convertirACategoriaDTO(buscada);
        return new ResponseEntity<>(categoriaDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoriaDTO> registrarCategoria(@RequestBody CategoriaDTO categoriaDTO){
        Categoria nuevaCategoria = convertirADominio(categoriaDTO);
        Categoria guardada = categoriaService.registrarCategoria(nuevaCategoria);
        CategoriaDTO dto = convertirACategoriaDTO(guardada);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idCategoria}/actividad/{idActividad}")
    public ResponseEntity<ActividadDTO> asignarActividad(@PathVariable long idCategoria,@PathVariable long idActividad){
        Actividad nueva = categoriaService.asignarActividad(idCategoria,idActividad);
        ActividadDTO dto = convertirAActividadDTO(nueva);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private CategoriaDTO convertirACategoriaDTO(Categoria entidad){
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(entidad.getIdCategoria());
        dto.setNombreCategoria(entidad.getNombreCategoria());
        return dto;
    }

    private Categoria convertirADominio(CategoriaDTO dto){

        Categoria entidad = new Categoria();
        entidad.setIdCategoria(dto.getIdCategoria());
        entidad.setNombreCategoria(dto.getNombreCategoria());
        return entidad;
    }

    private ActividadDTO convertirAActividadDTO(Actividad entidad) {
        ActividadDTO dto = new ActividadDTO();
        dto.setIdActividad(entidad.getIdActividad());
        dto.setNombreActividad(entidad.getNombreActividad());
        return dto;
    }
}
