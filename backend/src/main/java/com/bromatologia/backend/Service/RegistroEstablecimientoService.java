package com.bromatologia.backend.Service;

import com.bromatologia.backend.DTO.CategoriaDTO;
import com.bromatologia.backend.DTO.EmpresaUpdateDTO;
import com.bromatologia.backend.DTO.RegistroEstUpdateDTO;
import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Exception.*;
import com.bromatologia.backend.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroEstablecimientoService {
    @Autowired
    private IRegistroEstablecimientoRepository registroEstablecimientoRepository;

    @Autowired
    private IEmpresaRepository empresaRepository;

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    public List<RegistroEstablecimiento> obtenerEstablecimientos() {
        return registroEstablecimientoRepository.findAll();
    }

    public RegistroEstablecimiento obtenerEstablecimientoById(String id) {
       return obtenerRegistroEstablecimientoExistente(id);
    }


    public RegistroEstablecimiento guardarRegistro(RegistroEstablecimiento registro) {
        if(registro == null){
            throw new RegistroEstablecimientoException("El registro no puede ser nulo");
        }
        return registroEstablecimientoRepository.save(registro);
    }

    public List<RegistroEstablecimiento> obtenerTodosConEstablecimientoYMantenimiento(){
        return registroEstablecimientoRepository.findAllConEstablecimientoYMantenimientos();
    }

    public List<RegistroEstablecimiento> obtenerEstablecimientosYCategorias(){
        return registroEstablecimientoRepository.findAllConEstablecimientoYCategorias();
    }

    public RegistroEstablecimiento actualizarRegistroEstablecimiento(String rpe, RegistroEstUpdateDTO dto) {
        if (dto == null || rpe.isEmpty()) {
            throw new EmpresaException("Empresa no encontrada o cuit invalido");
        }
        RegistroEstablecimiento aActualizar = obtenerRegistroEstablecimientoExistente(rpe);
        aActualizar.setFechaEmision(dto.getFechaEmision());
        aActualizar.setFechaVencimiento(dto.getFechaVencimiento());
        aActualizar.setDepartamentoEst(dto.getDepartamento());
        aActualizar.setLocalidadEst(dto.getLocalidad());
        aActualizar.setDireccionEst(dto.getDireccion());
        aActualizar.setEnlace(dto.getEnlace());
        return registroEstablecimientoRepository.save(aActualizar);
    }

    public void eliminarRegistro(String id) {
        if(id.isEmpty()){
            throw new RegistroEstablecimientoException("El id es invalido");
        }
        RegistroEstablecimiento aEliminar = obtenerRegistroEstablecimientoExistente(id);
        registroEstablecimientoRepository.delete(aEliminar);
    }

    @Transactional
    public Categoria asignarCategoria(String idRegistroEstablecimiento, long idCategoria){
        RegistroEstablecimiento registroEst = obtenerRegistroEstablecimientoExistente(idRegistroEstablecimiento);
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow( () -> new CategoriaException("La categoria no existe"));
        registroEst.agregarCategoria(categoria);
        return categoria;
    }


    @Transactional
    public Mantenimiento agregarMantenimiento(String idRegistroEstablecimiento,long idMantenimiento){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(idRegistroEstablecimiento);
        Mantenimiento mantenimiento = mantenimientoRepository.findById(idMantenimiento).orElseThrow(() -> new MantenimientoException("no se ha encontrado el mantenimiento"));
        registro.agregarMantenimiento(mantenimiento);
        return mantenimiento;
    }


    public RegistroEstablecimiento obtenerRegistroEstablecimientoExistente(String id){
        if(id.isEmpty()){
            throw new RegistroEstablecimientoException("El id del registro es invalido");
        }
        return registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
    }


}
