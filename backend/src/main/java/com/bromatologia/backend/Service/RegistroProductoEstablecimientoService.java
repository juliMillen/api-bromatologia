package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.RegistroEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProducto;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimientoId;
import com.bromatologia.backend.Exception.RegistroEstablecimientoException;
import com.bromatologia.backend.Exception.RegistroProductoEstablecimientoException;
import com.bromatologia.backend.Exception.RegistroProductoException;
import com.bromatologia.backend.Repository.IRegistroEstablecimientoRepository;
import com.bromatologia.backend.Repository.IRegistroProductoEstablecimientoRepository;
import com.bromatologia.backend.Repository.IRegistroProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroProductoEstablecimientoService {
    @Autowired
    private IRegistroProductoEstablecimientoRepository registroProductoEstablecimientoRepository;

    @Autowired
    private IRegistroEstablecimientoRepository registroEstablecimientoRepository;

    @Autowired
    private IRegistroProductoRepository registroProductoRepository;

    public List<RegistroProductoEstablecimiento> obtenerRegistroProductoEstablecimiento() {
        return registroProductoEstablecimientoRepository.findAll();
    }

    public RegistroProductoEstablecimiento obtenerRegistroProductoEstablecimientoPorId(long idProducto, long idEstablecimiento) {
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId(idProducto,idEstablecimiento);
        return registroProductoEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroProductoEstablecimientoException("El registro del establecimiento no existe"));
    }

    public RegistroProductoEstablecimiento crearRegistroProductoEstablecimiento(RegistroProductoEstablecimiento reg) {
        if(reg == null || reg.getId() == null) {
            throw new IllegalArgumentException("El registro o su id no pueden ser nulo");

        }
        long idProducto = reg.getId().getRegistroProductoId();
        long idEstablecimiento = reg.getId().getRegistroEstablecimientoId();

        //obtener las entidades relacionadas

        RegistroProducto producto = registroProductoRepository.findById(idProducto).orElseThrow(() -> new RegistroProductoException("El registro del producto no existe"));
        RegistroEstablecimiento establecimiento = registroEstablecimientoRepository.findById(idEstablecimiento).orElseThrow(() -> new RegistroEstablecimientoException("El registro del establecimiento no existe"));

        //asigno las entidades
        reg.setRegistroProducto(producto);
        reg.setRegistroEstablecimiento(establecimiento);

        //asegurar que el ID embebido este bien construido
        reg.setId(new RegistroProductoEstablecimientoId(idProducto,idEstablecimiento));
        return registroProductoEstablecimientoRepository.save(reg);
    }

    public void eliminarRegistroProductoEstablecimiento(long idProducto, long idEstablecimiento) {
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId(idProducto,idEstablecimiento);
        registroProductoEstablecimientoRepository.deleteById(id);
    }
}
