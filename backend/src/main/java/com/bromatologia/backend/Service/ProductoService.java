package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Exception.ProductoException;
import com.bromatologia.backend.Repository.IEstablecimientoRepository;
import com.bromatologia.backend.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;

    public List<Producto> obtenerTodosProductos(){
       return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(long id){
        return obtenerProductoExistente(id);
    }

    public Producto registrarProducto(Producto producto) {
        if(producto == null) {
            throw new ProductoException("Los campos no pueden ser nulos");
        }

        if(producto.getEstablecimiento() != null) {
            long idEstablecimiento = producto.getEstablecimiento().getIdEstablecimiento();
            Establecimiento establecimiento = establecimientoRepository.findById(idEstablecimiento).orElseThrow(()-> new ProductoException("No existe el establecimiento"));
            producto.setEstablecimiento(establecimiento);
        }
        return productoRepository.save(producto);
    }

    public void eliminarProductoPorId(long id){
        if(id <= 0){
            throw new ProductoException("El id no es valido");
        }
        Producto aEliminar = obtenerProductoExistente(id);
        productoRepository.delete(aEliminar);
    }


    public Producto editarProducto(long id,Producto producto) {
        if (producto == null || id <= 0) {
            throw new ProductoException("Los campos no pueden ser nulos");
        }
        Producto aActualizar = obtenerProductoExistente(id);
        aActualizar.setDenominacion(producto.getDenominacion());
        aActualizar.setMarca(producto.getMarca());
        aActualizar.setNombreFantasia(producto.getNombreFantasia());
        return productoRepository.save(aActualizar);
    }

    public Producto obtenerProductoExistente(long id_producto){
        if(id_producto <= 0){
            throw new ProductoException("El id no es valido");
        }
        return productoRepository.findById(id_producto).orElseThrow(() -> new ProductoException("El id no se ha encontrado"));
    }
}
