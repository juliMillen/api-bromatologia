package com.bromatologia.backend.Service;

import com.bromatologia.backend.DTO.RegistroEstUpdateDTO;
import com.bromatologia.backend.DTO.RegistroProdUpdateDTO;
import com.bromatologia.backend.Entity.Mantenimiento;
import com.bromatologia.backend.Entity.RegistroEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProducto;

import com.bromatologia.backend.Exception.EmpresaException;
import com.bromatologia.backend.Exception.RegistroProductoException;
import com.bromatologia.backend.Repository.IMantenimientoRepository;
import com.bromatologia.backend.Repository.IRegistroProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroProductoService {
    @Autowired
    private IRegistroProductoRepository registroProductoRepository;


    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    public List<RegistroProducto> obtenerRegistrosProducto() {
        return registroProductoRepository.findAll();
    }

    public RegistroProducto obtenerRegistroProducto(String id) {
        return obtenerRegistroProductoExistente(id);
    }

    public RegistroProducto guardarRegistroProducto(RegistroProducto registroProducto) {
        if(registroProducto == null){
            throw new RegistroProductoException("El registro no puede ser nulo");
        }
        return registroProductoRepository.save(registroProducto);
    }

    public RegistroProducto actualizarRegistroProducto(String rppa, RegistroProdUpdateDTO dto) {
        if (dto == null || rppa.isEmpty()) {
            throw new EmpresaException("Registro no encontrado o rppa invalido");
        }
        RegistroProducto aActualizar = obtenerRegistroProducto(rppa);
        aActualizar.setFechaEmision(dto.getFechaEmision());
        aActualizar.setFechaVencimiento(dto.getFechaVencimiento());
        aActualizar.setDenominacion(dto.getDenominacion());
        aActualizar.setMarca(dto.getMarca());
        aActualizar.setNombreFantasia(dto.getNombreFantasia());
        aActualizar.setCategoriaProducto(dto.getCategoriaProducto());
        aActualizar.setEnlace(dto.getEnlace());
        aActualizar.setExpediente(dto.getExpediente());
        aActualizar.setElaborador(dto.getElaborador());
        return registroProductoRepository.save(aActualizar);
    }

    public void eliminarRegistroProducto(String id) {
        if(id.isEmpty()){
            throw new RegistroProductoException("El id del registro no es valido");
        }
        RegistroProducto aEliminar = obtenerRegistroProductoExistente(id);
        registroProductoRepository.delete(aEliminar);
    }

    public List<RegistroProducto> obtenerTodosConProductoYMantenimiento(){
        return registroProductoRepository.findAllConProductoYMantenimientos();
    }


    @Transactional
    public Mantenimiento agregarMantenimiento(String idRegistroProducto, long idMantenimiento){
        RegistroProducto registroProd = obtenerRegistroProductoExistente(idRegistroProducto);
        Mantenimiento nuevo = mantenimientoRepository.findById(idMantenimiento).orElseThrow(() -> new RegistroProductoException("El mantenimiento no se ha encontrado"));
        registroProd.agregarMantenimiento(nuevo);
        return nuevo;
    }

    public RegistroProducto obtenerRegistroProductoExistente(String id){
        if(id.isEmpty()){
            throw new RegistroProductoException("El id del registro no puede ser negativo");
        }
        return registroProductoRepository.findById(id).orElseThrow(() -> new RegistroProductoException("El id del registro no existe"));
    }
}
