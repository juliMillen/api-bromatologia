package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Mantenimiento;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Entity.RegistroEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProducto;
import com.bromatologia.backend.Exception.RegistroProductoException;
import com.bromatologia.backend.Repository.IMantenimientoRepository;
import com.bromatologia.backend.Repository.IProductoRepository;
import com.bromatologia.backend.Repository.IRegistroEstablecimientoRepository;
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
    private IRegistroEstablecimientoRepository registroEstablecimientoRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    public List<RegistroProducto> obtenerRegistrosProducto() {
        return registroProductoRepository.findAll();
    }

    public RegistroProducto obtenerRegistroProducto(long id) {
        if(id <= 0){
            throw new RegistroProductoException("El id del registro no puede ser negativo");
        }
        return registroProductoRepository.findById(id).orElseThrow(() -> new RegistroProductoException("El id del registro no existe"));
    }

    public RegistroProducto guardarRegistroProducto(RegistroProducto registroProducto) {
        if(registroProducto == null){
            throw new RegistroProductoException("El registro no puede ser nulo");
        }
        return registroProductoRepository.save(registroProducto);
    }

    public void eliminarRegistroProducto(long id) {
        if(id <= 0){
            throw new RegistroProductoException("El id del registro no es valido");
        }
        RegistroProducto aEliminar = registroProductoRepository.findById(id).orElseThrow(() -> new RegistroProductoException("El id del registro no existe"));
        registroProductoRepository.delete(aEliminar);
    }

    @Transactional
    public Producto asignarProducto(long id, Producto producto){
        RegistroProducto registro = registroProductoRepository.findById(id).orElseThrow(() -> new RegistroProductoException("El id del registro no se ha encontrado"));
        productoRepository.save(producto);
        registro.asignarProducto(producto);
        return producto;
    }

    @Transactional
    public RegistroEstablecimiento asignarRegistroEstablecimiento(long id, RegistroEstablecimiento registroEstablecimiento){
        RegistroProducto registro = registroProductoRepository.findById(id).orElseThrow(() -> new RegistroProductoException("El id del registro no se ha encontrado"));
        registro.asignarRegistroEstablecimientos(registroEstablecimiento);
        registroEstablecimientoRepository.save(registroEstablecimiento);
        return registroEstablecimiento;
    }

    @Transactional
    public Mantenimiento agregarMantenimiento(long id, Mantenimiento mantenimiento){
        RegistroProducto registro = registroProductoRepository.findById(id).orElseThrow(() -> new RegistroProductoException("El id del registro no se encontro"));
        registro.agregarMantenimiento(mantenimiento);
        mantenimientoRepository.save(mantenimiento);
        return mantenimiento;
    }
}
