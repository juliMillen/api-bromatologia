package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Exception.EstablecimientoException;
import com.bromatologia.backend.Exception.ProductoException;
import com.bromatologia.backend.Repository.IEstablecimientoRepository;
import com.bromatologia.backend.Repository.IProductoRepository;
import jakarta.transaction.Transactional;
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

    public Establecimiento obtenerEstablecimientoPorId(long id){
     return obtenerEstablecimientoExistente(id);
    }

    public Establecimiento crearEstablecimiento(Establecimiento establecimiento){
        if(establecimiento == null){
            throw new EstablecimientoException("Los datos del establecimiento no pueden ser nulos");
        }
        return establecimientoRepository.save(establecimiento);
    }

    public void eliminarEstablecimiento(long id){
        if(id <= 0){
            throw new EstablecimientoException("Id invalido");
        }
        Establecimiento aEliminar = obtenerEstablecimientoExistente(id);
        establecimientoRepository.delete(aEliminar);
    }

    @Transactional
    public Producto agregarProducto(long idEstablecimiento,long idProducto){
        Establecimiento establecimiento = obtenerEstablecimientoExistente(idEstablecimiento);
        Producto producto = productoRepository.findById(idProducto).orElseThrow(() -> new ProductoException("Producto invalido"));
        establecimiento.agregarProductos(producto);
        return producto;
    }

    public Establecimiento obtenerEstablecimientoExistente(long idEstablecimiento){
        if(idEstablecimiento <= 0){
            throw new EstablecimientoException("id invalido");
        }
        return establecimientoRepository.findById(idEstablecimiento).orElseThrow(() -> new EstablecimientoException("Establecimiento no encontrado"));
    }
}

