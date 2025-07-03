package com.bromatologia.backend.Controller;
import com.bromatologia.backend.DTO.ProductoDTO;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/producto")
//@CrossOrigin("http://localhost:4200/")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public ResponseEntity<List<ProductoDTO>> listaProductos() {
        List<Producto> listaProductos = productoService.obtenerTodosProductos();
        if(listaProductos == null || listaProductos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ProductoDTO> listaDTO = listaProductos
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable long id) {
        Producto buscado = productoService.obtenerProductoPorId(id);
        ProductoDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<ProductoDTO> agregarProducto(@RequestBody @Valid ProductoDTO dto) {

        Producto producto = convertirADominio(dto);
        Producto guardado = productoService.registrarProducto(producto);
        ProductoDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable long id, @RequestBody @Valid ProductoDTO dto) {
        Producto actualizado = productoService.editarProducto(id, convertirADominio(dto));
        ProductoDTO respuesta = convertirADTO(actualizado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> eliminarProducto(@PathVariable long id) {
        productoService.eliminarProductoPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //metodos de mapeo DTO <---> entidad
    private ProductoDTO convertirADTO(Producto entidad) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(entidad.getIdProducto());
        dto.setMarca(entidad.getMarca());
        dto.setDenominacion(entidad.getDenominacion());
        dto.setNombreFantasia(entidad.getNombreFantasia());
        return dto;
    }

    private Producto convertirADominio(ProductoDTO dto) {
        Producto entidad = new Producto();
        entidad.setIdProducto(dto.getIdProducto());
        entidad.setMarca(dto.getMarca());
        entidad.setDenominacion(dto.getDenominacion());
        entidad.setNombreFantasia(dto.getNombreFantasia());
        return entidad;
    }

}
