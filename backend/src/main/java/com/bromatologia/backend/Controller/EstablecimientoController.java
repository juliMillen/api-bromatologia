package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.EstablecimientoDTO;
import com.bromatologia.backend.DTO.ProductoDTO;
import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Service.EstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/establecimiento")
//@CrossOrigin("http://localhost:4200/")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @GetMapping("/")
    public ResponseEntity<List<EstablecimientoDTO>> obtenerEstablecimientos(){

        List<Establecimiento> listaEstablecimientos = establecimientoService.obtenerEstablecimientos();
        if(listaEstablecimientos == null ||listaEstablecimientos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<EstablecimientoDTO> listaDTO = listaEstablecimientos
                .stream()
                .map( this::convertirADTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstablecimientoDTO> obtenerEstablecimientoPorId(@PathVariable long id){
        Establecimiento buscado = establecimientoService.obtenerEstablecimientoPorId(id);
        EstablecimientoDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosDeEstablecimientos(@PathVariable long id) {
        Establecimiento buscado = establecimientoService.obtenerEstablecimientoPorId(id);

        List<ProductoDTO>listaDTO = buscado.getProductos()
                .stream()
                .map(this::convertirAProductoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<EstablecimientoDTO> crearEstablecimiento(@RequestBody @Valid EstablecimientoDTO dto){

    Establecimiento nuevoEstablecimiento = convertirADominio(dto);
    Establecimiento guardado = establecimientoService.crearEstablecimiento(nuevoEstablecimiento);
    EstablecimientoDTO respuesta = convertirADTO(guardado);
    return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idEstablecimiento}/productos/{idProducto}")
    public ResponseEntity<Producto> agregarProducto(@PathVariable long idEstablecimiento, @PathVariable long idProducto){
        Producto nuevo = establecimientoService.agregarProducto(idEstablecimiento, idProducto);
        return ResponseEntity.ok(nuevo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Establecimiento> eliminarEstablecimiento(@PathVariable long id){
        establecimientoService.eliminarEstablecimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //metodos de mapeo DTO <---> entidad
    private EstablecimientoDTO convertirADTO(Establecimiento entidad){
        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setIdEstablecimiento(entidad.getIdEstablecimiento());
        dto.setDepartamento(entidad.getDepartamento());
        dto.setLocalidad(entidad.getLocalidad());
        dto.setDireccion(entidad.getDireccion());
        if(entidad.getEmpresa() != null){
            dto.setCuitEmpresa(entidad.getEmpresa().getCuitEmpresa());
        }else{
            dto.setCuitEmpresa(0);
        }
        //Producto
        List<ProductoDTO> productosDTO = entidad.getProductos()
                .stream()
                .map(e->{
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setIdProducto(e.getIdProducto());
                    productoDTO.setDenominacion(e.getDenominacion());
                    productoDTO.setMarca(e.getMarca());
                    productoDTO.setNombreFantasia(e.getNombreFantasia());
                    return productoDTO;
                }).toList();
        dto.setProductos(productosDTO);
        return dto;
    }

    private Establecimiento convertirADominio(EstablecimientoDTO dto){

        Establecimiento entidad = new Establecimiento();
        entidad.setIdEstablecimiento(dto.getIdEstablecimiento());
        entidad.setDepartamento(dto.getDepartamento());
        entidad.setLocalidad(dto.getLocalidad());
        entidad.setDireccion(dto.getDireccion());


        //Empresa
        if(dto.getCuitEmpresa() != 0){
            Empresa empresa = new Empresa();
            empresa.setCuitEmpresa(dto.getCuitEmpresa());
            entidad.setEmpresa(empresa);
        }else{
            entidad.setEmpresa(null);
        }

        //Producto
        List<Producto> productos = dto.getProductos()
                .stream()
                .map(e->{
                    Producto prod = new Producto();
                    prod.setIdProducto(e.getIdProducto());
                    prod.setDenominacion(e.getDenominacion());
                    prod.setMarca(e.getMarca());
                    prod.setNombreFantasia(e.getNombreFantasia());
                    return prod;
                }).toList();
        entidad.setProductos(productos);
        return entidad;
    }

    private ProductoDTO convertirAProductoDTO(Producto e){
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(e.getIdProducto());
        dto.setDenominacion(e.getDenominacion());
        dto.setMarca(e.getMarca());
        dto.setNombreFantasia(e.getNombreFantasia());
        return dto;
    }

}
