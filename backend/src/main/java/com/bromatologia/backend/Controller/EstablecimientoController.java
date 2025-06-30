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

@RestController
@RequestMapping("/establecimiento")
//@CrossOrigin("http://localhost:4200/")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @GetMapping("/")
    public ResponseEntity<List<Establecimiento>> obtenerEstablecimientos(){
        List<Establecimiento> listaEstablecimiento = establecimientoService.obtenerEstablecimientos();
        if(listaEstablecimiento.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaEstablecimiento, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstablecimientoDTO> obtenerEstablecimientoPorId(@PathVariable long id){
        Establecimiento buscado = establecimientoService.obtenerEstablecimientoPorId(id);
        EstablecimientoDTO respuesta = convertirADTO(buscado);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<List<Producto>> obtenerProductosDeEstablecimientos(@PathVariable long id) {
        Establecimiento buscado = establecimientoService.obtenerEstablecimientoPorId(id);
        return new ResponseEntity<>(buscado.getProductos(), HttpStatus.OK);
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
        dto.setCuitEmpresa(entidad.getEmpresa().getCuitEmpresa());

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
        Empresa empresa = new Empresa();
        empresa.setCuitEmpresa(dto.getCuitEmpresa());
        entidad.setEmpresa(empresa);

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

}
