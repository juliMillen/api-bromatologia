package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Exception.EstablecimientoException;
import com.bromatologia.backend.Repository.IEstablecimientoRepository;
import com.bromatologia.backend.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoService {

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;

    @Autowired
    private IProductoRepository productoRepository;


    public List<Establecimiento> obtenerEstablecimientos(){
        return establecimientoRepository.findAll();
    }

    public Establecimiento obtenerEstablecimientoPorId(Long id){
        if(id <= 0){
            throw new EstablecimientoException("id invalido");
        }
        return establecimientoRepository.findById(id).orElseThrow(() -> new EstablecimientoException("Establecimiento no encontrado"));
    }

    public Establecimiento crearEstablecimiento(Establecimiento establecimiento){
        if(establecimiento == null){
            throw new EstablecimientoException("Los datos del establecimiento no pueden ser nulos");
        }
        return establecimientoRepository.save(establecimiento);
    }

    public void eliminarEstablecimiento(Long id){
        if(id <= 0){
            throw new EstablecimientoException("Id invalido");
        }
        Establecimiento aEliminar = establecimientoRepository.findById(id).orElseThrow(() -> new EstablecimientoException("Establecimiento no encontrado"));
        establecimientoRepository.delete(aEliminar);
    }

    public Producto agregarProducto(Long id,Producto producto){
        Establecimiento establecimiento = establecimientoRepository.findById(id).orElseThrow(() -> new EstablecimientoException("Establecimiento no encontrado"));
        producto.setEstablecimiento(establecimiento);
        establecimiento.agregarProductos(producto);
        productoRepository.save(producto);
        return producto;
    }
}

