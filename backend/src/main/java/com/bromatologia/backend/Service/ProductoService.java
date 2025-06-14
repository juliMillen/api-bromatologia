package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Exception.ProductoException;
import com.bromatologia.backend.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private IProductoRepository productoRepository;

    public List<Producto> obtenerTodosProductos(){
       return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(long id){
        if(id <= 0){
            throw new ProductoException("El id no es valido");
        }
        return productoRepository.findById(id).orElseThrow(() -> new ProductoException("El id no se ha encontrado"));
    }

    public Producto registrarProducto(Producto producto) {
        if(producto == null) {
            throw new ProductoException("Los campos no pueden ser nulos");
        }
        return productoRepository.save(producto);
    }

    public void eliminarProductoPorId(long id){
        if(id <= 0){
            throw new ProductoException("El id no es valido");
        }
        Producto aEliminar = productoRepository.findById(id).orElseThrow(() -> new ProductoException("El id no existe"));
        productoRepository.delete(aEliminar);
    }


    public Producto editarProducto(Producto producto) {
        if (producto == null) {
            throw new ProductoException("Los campos no pueden ser nulos");
        }
        Producto aActualizar = productoRepository.findById(producto.getId_Producto()).orElseThrow(() -> new ProductoException("El id no encontrado"));
        aActualizar.setDenominacion(producto.getDenominacion());
        aActualizar.setMarca(producto.getMarca());
        aActualizar.setNombreFantasia(producto.getNombreFantasia());
        return productoRepository.save(aActualizar);
    }
}
