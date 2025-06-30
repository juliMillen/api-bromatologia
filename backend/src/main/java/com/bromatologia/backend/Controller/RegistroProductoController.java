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

@RestController
@RequestMapping("/registroProducto")
//@CrossOrigin("http://localhost:4200/")
public class RegistroProductoController {
    @Autowired
    private RegistroProductoService registroProductoService;

    @GetMapping("/")
    public ResponseEntity<List<RegistroProducto>> obtenerRegistrosProductos() {
       List<RegistroProducto> listaRegistrosProd = registroProductoService.obtenerRegistrosProducto();
       return new ResponseEntity<>(listaRegistrosProd, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroProductoDTO> obtenerRegistroProductoById(@PathVariable long id) {
        RegistroProducto buscado = registroProductoService.obtenerRegistroProducto(id);
        RegistroProductoDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<RegistroProductoDTO> guardarRegistroProducto(@RequestBody  @Valid RegistroProductoDTO dto) {
        RegistroProducto nuevoRegistroProducto = convertirADominio(dto);
        RegistroProducto guardado = registroProductoService.guardarRegistroProducto(nuevoRegistroProducto);
        RegistroProductoDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegistroProducto}/producto/{idProducto}")
    public ResponseEntity<Producto> asignarProducto(@PathVariable long idRegistroProducto, @PathVariable long idProducto) {
        Producto nuevo = registroProductoService.asignarProducto(idRegistroProducto, idProducto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
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
        dto.setIdRegistroProducto(entidad.getIdRegistroProducto());
        dto.setTipo(entidad.getTipo());

        //Producto
        if(entidad.getProducto() != null){
            dto.setIdProducto(entidad.getProducto().getIdProducto());
        }


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
        entidad.setIdRegistroProducto(dto.getIdRegistroProducto());
        entidad.setTipo(dto.getTipo());

        //producto
        Producto producto = new Producto();
        producto.setIdProducto(dto.getIdProducto());
        entidad.setProducto(producto);


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
