package com.bromatologia.backend.Controller;

import com.bromatologia.backend.DTO.*;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Exception.RegistroProductoException;
import com.bromatologia.backend.Service.RegistroEstablecimientoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
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
    public ResponseEntity<RegistroEstablecimientoDTO>obtenerRegistroEstablecimientoById(@PathVariable String id) {
        RegistroEstablecimiento buscado = registroEstablecimientoService.obtenerEstablecimientoById(id);
        if(buscado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RegistroEstablecimientoDTO dto = convertirADTO(buscado);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public ResponseEntity<List<CategoriaDTO>> categoriaDeRegistro(@PathVariable String idRegistro){
        RegistroEstablecimiento registro = registroEstablecimientoService.obtenerEstablecimientoById(idRegistro);

        List<CategoriaDTO> listaDTO = registro.getListaCategorias()
                .stream()
                .map(this::convertirACategoriaDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<RegistroEstablecimientoDTO> guardarRegistro(@RequestBody @Valid RegistroEstablecimientoDTO dto) {
        RegistroEstablecimiento nuevoRegistro = convertirADominio(dto);
        RegistroEstablecimiento guardado = registroEstablecimientoService.guardarRegistro(nuevoRegistro);
        RegistroEstablecimientoDTO respuesta = convertirADTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping("/registroEstablecimientoConMantenimientos")
    public ResponseEntity<List<RegistroEstablecimientoDTO>> obtenerConEstablecimientoYMantenimiento(){
        List<RegistroEstablecimiento> listaRegistrosConMantenimiento = registroEstablecimientoService.obtenerTodosConEstablecimientoYMantenimiento();
        if(listaRegistrosConMantenimiento == null || listaRegistrosConMantenimiento.isEmpty()){
            throw new RegistroProductoException("No se han encontrado los registros con mantenimiento");
        }

        List<RegistroEstablecimientoDTO> listaDTO = listaRegistrosConMantenimiento
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    @GetMapping("/registroEstablecimientoConCategorias")
    public ResponseEntity<List<RegistroEstablecimientoDTO>> obtenerConEstablecimientoYCategoria(){
        List<RegistroEstablecimiento> listaRegistrosConCategorias = registroEstablecimientoService.obtenerEstablecimientosYCategorias();
        if(listaRegistrosConCategorias == null || listaRegistrosConCategorias.isEmpty()){
            throw new RegistroProductoException("No hay categorias asociadas");
        }

        List<RegistroEstablecimientoDTO> listaDTO = listaRegistrosConCategorias
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.OK);
    }

    /*@PreAuthorize("isAuthenticated()")
    @PostMapping("{idRegistroEstablecimiento}/categoria/{idCategoria}")
    public ResponseEntity<CategoriaDTO> asignarCategoria(@PathVariable String idRegistroEstablecimiento, @PathVariable long idCategoria) {
        Categoria nueva = registroEstablecimientoService.asignarCategoria(idRegistroEstablecimiento,idCategoria);
        CategoriaDTO dto = convertirACategoriaDTO(nueva);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{idRegistroEstablecimiento}/categorias/{idCategorias}")
    public ResponseEntity<Set<CategoriaDTO>> asignarCategorias(@PathVariable String idRegistroEstablecimiento, @PathVariable List<Long> idCategorias){
        Set<Categoria> nuevas = registroEstablecimientoService.asignarCategorias(idRegistroEstablecimiento,idCategorias);
        Set<CategoriaDTO> dtos = nuevas.stream()
                .map(this::convertirACategoriaDTO)
                .collect(Collectors.toSet());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("{idRegistroEstablecimiento}/mantenimiento/{idMantenimiento}")
    public ResponseEntity<MantenimientoDTO> agregarMantenimiento(@PathVariable String idRegistroEstablecimiento, @PathVariable long idMantenimiento) {
        Mantenimiento nuevo = registroEstablecimientoService.agregarMantenimiento(idRegistroEstablecimiento, idMantenimiento);
        MantenimientoDTO dto = convertirAMantenimientoDTO(nuevo);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{rpe}")
    public ResponseEntity<RegistroEstablecimientoDTO> actualizarRegistro(@PathVariable String rpe, @RequestBody @Valid RegistroEstUpdateDTO dto) {
        RegistroEstablecimiento actualizado = registroEstablecimientoService.actualizarRegistroEstablecimiento(rpe, dto);
        RegistroEstablecimientoDTO dtoActualizado = convertirADTO(actualizado);
        return new ResponseEntity<>(dtoActualizado, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<RegistroEstablecimiento> eliminarRegistro(@PathVariable String id) {
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
        empresaDTO.setCuitEmpresa(entidad.getEmpresa().getCuitEmpresa());
        empresaDTO.setRazonSocial(entidad.getEmpresa().getRazonSocial());
        dto.setEmpresa(empresaDTO);

        //Categorias
        List<CategoriaDTO> categoriaDTO = entidad.getListaCategorias()
                .stream()
                .map(e ->{
                    CategoriaDTO cat = new CategoriaDTO();
                    cat.setIdCategoria(e.getIdCategoria());
                    cat.setNombreCategoria(e.getNombreCategoria());
                    return cat;
                }).toList();
        dto.setCategorias(categoriaDTO);


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
        empresa.setCuitEmpresa(dto.getEmpresa().getCuitEmpresa());
        empresa.setRazonSocial(dto.getEmpresa().getRazonSocial());
        entidad.setEmpresa(empresa);

        //Categoria
        Set<Categoria> categorias = dto.getCategorias()
                .stream()
                .map(e->{
                    Categoria cat = new Categoria();
                    cat.setIdCategoria(e.getIdCategoria());
                    cat.setNombreCategoria(e.getNombreCategoria());
                    return cat;
                }).collect(Collectors.toSet());
        entidad.setListaCategorias(categorias);


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

    //categoria a dto
    private CategoriaDTO convertirACategoriaDTO(Categoria entidad){
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(entidad.getIdCategoria());
        dto.setNombreCategoria(entidad.getNombreCategoria());
        return dto;
    }

    //mantenimiento a dto
    //metodos de mapeo DTO <---> entidad
    private MantenimientoDTO convertirAMantenimientoDTO(Mantenimiento entidad) {
        MantenimientoDTO dto = new MantenimientoDTO();
        dto.setIdMantenimiento(entidad.getIdMantenimiento());
        dto.setFechaMantenimiento(entidad.getFechaMantenimiento());
        dto.setEnlaceRecibido(entidad.getEnlaceRecibido());

        //Tramites
        List<TramiteDTO> tramites = entidad.getTramites()
                .stream()
                .map(e->{
                    TramiteDTO tramite = new TramiteDTO();
                    tramite.setIdTramite(e.getIdTramite());
                    tramite.setNombreTramite(e.getNombreTramite());

                    ReciboDTO recibo = new ReciboDTO();
                    recibo.setFechaRecibo(e.getRecibo().getFechaRecibo());
                    recibo.setImporte(e.getRecibo().getImporte());

                    tramite.setRecibo(recibo);
                    return tramite;
                }).toList();
        dto.setTramites(tramites);
        return dto;
    }
}
