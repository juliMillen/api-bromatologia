package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Mantenimiento;
import com.bromatologia.backend.Entity.Producto;
import com.bromatologia.backend.Entity.RegistroProducto;
import com.bromatologia.backend.Exception.ProductoException;
import com.bromatologia.backend.Exception.RegistroProductoException;
import com.bromatologia.backend.Repository.IMantenimientoRepository;
import com.bromatologia.backend.Repository.IProductoRepository;
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
    private IProductoRepository productoRepository;

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    public List<RegistroProducto> obtenerRegistrosProducto() {
        return registroProductoRepository.findAll();
    }

    public RegistroProducto obtenerRegistroProducto(long id) {
        return obtenerRegistroProductoExistente(id);
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
        RegistroProducto aEliminar = obtenerRegistroProductoExistente(id);
        registroProductoRepository.delete(aEliminar);
    }

    @Transactional
    public Producto asignarProducto(long idRegistroProducto, long idProducto){
        RegistroProducto registro = obtenerRegistroProductoExistente(idRegistroProducto);
        Producto nuevo = productoRepository.findById(idProducto).orElseThrow(() -> new ProductoException("El producto no existe"));
        registro.asignarProducto(nuevo);
        return nuevo;
    }


    @Transactional
    public Mantenimiento agregarMantenimiento(long idRegistroProducto, long idMantenimiento){
        RegistroProducto registroProd = obtenerRegistroProductoExistente(idRegistroProducto);
        Mantenimiento nuevo = mantenimientoRepository.findById(idMantenimiento).orElseThrow(() -> new RegistroProductoException("El mantenimiento no se ha encontrado"));
        registroProd.agregarMantenimiento(nuevo);
        return nuevo;
    }

    public RegistroProducto obtenerRegistroProductoExistente(long id){
        if(id <= 0){
            throw new RegistroProductoException("El id del registro no puede ser negativo");
        }
        return registroProductoRepository.findById(id).orElseThrow(() -> new RegistroProductoException("El id del registro no existe"));
    }
}
