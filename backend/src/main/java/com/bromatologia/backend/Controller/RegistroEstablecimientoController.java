package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.*;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Service.RegistroEstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registroEstablecimiento")
//@CrossOrigin("http://localhost:4200/")
public class RegistroEstablecimientoController {
    @Autowired
    private RegistroEstablecimientoService registroEstablecimientoService;


    @GetMapping()
    public ResponseEntity<List<RegistroEstablecimientoDTO>> obtenerRegistroEstablecimiento() {
        List<RegistroEstablecimiento> listaRegistrosEst = registroEstablecimientoService.obtenerEstablecimientos();
        if(listaRegistrosEst == null || listaRegistrosEst.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<RegistroEstablecimientoDTO> listaDTO = listaRegistrosEst
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroEstablecimientoDTO>obtenerRegistroEstablecimientoById(@PathVariable long id) {
        RegistroEstablecimiento buscado = registroEstablecimientoService.obtenerEstablecimientoById(id);
        if(buscado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RegistroEstablecimientoDTO dto = convertirADTO(buscado);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<RegistroEstablecimientoDTO> guardarRegistro(@RequestBody @Valid RegistroEstablecimientoDTO dto) {
        RegistroEstablecimiento nuevoRegistro = convertirADominio(dto);
        RegistroEstablecimiento guardado = registroEstablecimientoService.guardarRegistro(nuevoRegistro);
        RegistroEstablecimientoDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegistroEstablecimiento}/categoria/{idCategoria}")
    public ResponseEntity<Categoria> asignarCategoria(@PathVariable long idRegistroEstablecimiento, @PathVariable long idCategoria) {
        Categoria nueva = registroEstablecimientoService.asignarCategoria(idRegistroEstablecimiento,idCategoria);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("{idRegistroEstablecimiento}/mantenimiento/{idMantenimiento}")
    public ResponseEntity<Mantenimiento> agregarMantenimiento(@PathVariable long idRegistroEstablecimiento, @PathVariable long idMantenimiento) {
        Mantenimiento nuevo = registroEstablecimientoService.agregarMantenimiento(idRegistroEstablecimiento, idMantenimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroEstablecimiento> eliminarRegistro(@PathVariable long id) {
        registroEstablecimientoService.eliminarRegistro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private RegistroEstablecimientoDTO convertirADTO(RegistroEstablecimiento entidad){
        RegistroEstablecimientoDTO dto = new RegistroEstablecimientoDTO();
        dto.setRpe(entidad.getRpe());
        dto.setFechaEmision(entidad.getFechaEmision());
        dto.setFechaVencimiento(entidad.getFechaVencimiento());
        dto.setDepartamento(entidad.getDepartamentoEst());
        dto.setLocalidad(entidad.getLocalidadEst());
        dto.setDireccion(entidad.getDireccionEst());
        dto.setExpediente(entidad.getExpediente());
        dto.setEnlace(entidad.getEnlace());

        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setRazonSocial(entidad.getEmpresa().getRazonSocial());
        dto.setEmpresa(empresaDTO);

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


    private RegistroEstablecimiento convertirADominio(RegistroEstablecimientoDTO dto) {
        RegistroEstablecimiento entidad = new RegistroEstablecimiento();
        entidad.setRpe(dto.getRpe());
        entidad.setFechaEmision(dto.getFechaEmision());
        entidad.setFechaVencimiento(dto.getFechaVencimiento());
        entidad.setDepartamentoEst(dto.getDepartamento());
        entidad.setLocalidadEst(dto.getLocalidad());
        entidad.setDireccionEst(dto.getDireccion());
        entidad.setExpediente(dto.getExpediente());
        entidad.setEnlace(dto.getEnlace());


        //Empresa
        Empresa empresa = new Empresa();
        empresa.setRazonSocial(dto.getEmpresa().getRazonSocial());
        entidad.setEmpresa(empresa);


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
