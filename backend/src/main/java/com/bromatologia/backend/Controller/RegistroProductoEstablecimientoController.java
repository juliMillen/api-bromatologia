package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.MantenimientoDTO;
import com.bromatologia.backend.DTO.RegistroProductoDTO;
import com.bromatologia.backend.DTO.RegistroProductoEstablecimientoDTO;
import com.bromatologia.backend.Entity.RegistroProducto;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimientoId;
import com.bromatologia.backend.Exception.RegistroProductoEstablecimientoException;
import com.bromatologia.backend.Service.RegistroProductoEstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/registroProductoEstablecimiento")
public class RegistroProductoEstablecimientoController {
    @Autowired
    private RegistroProductoEstablecimientoService registroProductoEstablecimientoService;


    @GetMapping("/")
    public ResponseEntity<List<RegistroProductoEstablecimientoDTO>> obtenerRegistroProductoEstablecimiento() {
       List<RegistroProductoEstablecimiento> listaRegistrosEnt = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimiento();
       if(listaRegistrosEnt == null || listaRegistrosEnt.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       List<RegistroProductoEstablecimientoDTO> dtos = listaRegistrosEnt.stream()
               .map(this::convertirARegistroDTO)
               .collect(Collectors.toList());
       return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{idProducto}/{idEstablecimiento}")
    public ResponseEntity<RegistroProductoEstablecimientoDTO> obtenerRegistroProductoEstablecimientoPorId(@PathVariable long idProducto,@PathVariable long idEstablecimiento) {
        RegistroProductoEstablecimiento buscado = registroProductoEstablecimientoService.obtenerRegistroProductoEstablecimientoPorId(idProducto,idEstablecimiento);
        if(buscado == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertirARegistroDTO(buscado), HttpStatus.OK);

    }

    @GetMapping("/registroProductoConMantenimientos")
    public ResponseEntity<List<RegistroProductoEstablecimientoDTO>> obtenerConProductoYMantenimiento(){
        List<RegistroProductoEstablecimiento> listaRegistrosConMantenimiento = registroProductoEstablecimientoService.obtenerTodosConProductoYMantenimiento();
        if(listaRegistrosConMantenimiento == null || listaRegistrosConMantenimiento.isEmpty()){
            throw new RegistroProductoEstablecimientoException("No se han encontrado los registros con mantenimiento");
        }

        List<RegistroProductoEstablecimientoDTO> listaDTO = listaRegistrosConMantenimiento
                .stream()
                .map(this::convertirARegistroDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{idRegProducto}/{idRegEstablecimiento}")
    public ResponseEntity<RegistroProductoEstablecimientoDTO> guardarRegistroProductoEstablecimiento( @PathVariable long idRegProducto,@PathVariable long idRegEstablecimiento, @RequestBody @Valid RegistroProductoEstablecimientoDTO dto) {
        RegistroProductoEstablecimiento registro = convertirADominio(dto);
        RegistroProductoEstablecimiento regGuardado = registroProductoEstablecimientoService.crearRegistroProductoEstablecimiento(idRegProducto,idRegEstablecimiento,registro);
        RegistroProductoEstablecimientoDTO regGuardadoDTO = convertirARegistroDTO(regGuardado);
        return new ResponseEntity<>(regGuardadoDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idProducto}/{idEstablecimiento}")
    public ResponseEntity<Void> eliminarRegistroProductoEstablecimiento(@PathVariable long idProducto,@PathVariable long idEstablecimiento){
        registroProductoEstablecimientoService.eliminarRegistroProductoEstablecimiento(idProducto,idEstablecimiento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //metodos de mapeo DTO <---> entidad
    private RegistroProductoEstablecimientoDTO convertirARegistroDTO(RegistroProductoEstablecimiento entidad){
        RegistroProductoEstablecimientoDTO dto = new RegistroProductoEstablecimientoDTO();
        dto.setIdRegistroProducto(entidad.getId().getRegistroProductoId());
        dto.setIdRegistroEstablecimiento(entidad.getId().getRegistroEstablecimientoId());
        dto.setRnpaActual(entidad.getRnpaActual());
        dto.setFechaEmision(entidad.getFechaDeEmision());
        dto.setRnpaAnterior(entidad.getRnpaAnterior());
        dto.setTipo(entidad.getTipo());
        dto.setNroRne(entidad.getNroRne());
        dto.setCertificado(entidad.getCertificado());
        dto.setExpediente(entidad.getExpediente());


        //Registro Producto

        RegistroProducto rp = entidad.getRegistroProducto();
        if(rp != null){
            RegistroProductoDTO rpDTO = new RegistroProductoDTO();
            rpDTO.setIdRegistroProducto(rp.getIdRegistroProducto());
            rpDTO.setTipo(rp.getTipo());
            rpDTO.setIdProducto(rp.getProducto().getIdProducto());
            rpDTO.setElaborador(rp.getElaborador());

            //Mantenimientos

            if(rp.getMantenimientos() != null){
                List<MantenimientoDTO> mantenimientoDTO = rp.getMantenimientos()
                        .stream()
                        .map( man -> {
                            MantenimientoDTO mDTO = new MantenimientoDTO();
                            mDTO.setIdMantenimiento(man.getIdMantenimiento());
                            mDTO.setFechaMantenimiento(man.getFechaMantenimiento());
                            mDTO.setEnlaceRecibido(man.getEnlaceRecibido());
                            return mDTO;
                        })
                        .collect(Collectors.toList());
                rpDTO.setMantenimientos(mantenimientoDTO);
            }
            dto.setRegistroProducto(rpDTO);
        }
        return dto;
    }

    private RegistroProductoEstablecimiento convertirADominio(RegistroProductoEstablecimientoDTO dto){
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId();
        id.setRegistroProductoId(dto.getIdRegistroProducto());
        id.setRegistroEstablecimientoId(dto.getIdRegistroEstablecimiento());

        RegistroProductoEstablecimiento entidad = new RegistroProductoEstablecimiento();
        entidad.setId(id);
        entidad.setRnpaActual(dto.getRnpaActual());
        entidad.setFechaDeEmision(dto.getFechaEmision());
        entidad.setRnpaAnterior(dto.getRnpaAnterior());
        entidad.setTipo(dto.getTipo());
        entidad.setNroRne(dto.getNroRne());
        entidad.setCertificado(dto.getCertificado());
        entidad.setExpediente(dto.getExpediente());
        return entidad;
    }
}
