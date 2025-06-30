package com.bromatologia.backend.Service;

import com.bromatologia.backend.DTO.RegistroProductoEstablecimientoDTO;
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
import jakarta.transaction.Transactional;
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

    @Transactional
    public RegistroProductoEstablecimiento crearRegistroProductoEstablecimiento(long idRegProducto, long idRegEstablecimiento, RegistroProductoEstablecimiento entidad) {
        if(idRegProducto <= 0 || idRegEstablecimiento <= 0) {
            throw new IllegalArgumentException("El registro o su id no pueden ser nulo");
        }

        //obtener las entidades relacionadas

        RegistroProducto producto = registroProductoRepository.findById(idRegProducto).orElseThrow(() -> new RegistroProductoException("El registro del producto no existe"));
        RegistroEstablecimiento establecimiento = registroEstablecimientoRepository.findById(idRegEstablecimiento).orElseThrow(() -> new RegistroEstablecimientoException("El registro del establecimiento no existe"));

        //asigno las entidades
        RegistroProductoEstablecimientoId reg = new RegistroProductoEstablecimientoId(idRegProducto,idRegEstablecimiento);
        reg.setRegistroProductoId(producto.getIdRegistroProducto());
        reg.setRegistroEstablecimientoId(establecimiento.getIdRegistroEstablecimiento());

        entidad.setId(reg);
        entidad.setRegistroProducto(producto);
        entidad.setRegistroEstablecimiento(establecimiento);

        return registroProductoEstablecimientoRepository.save(entidad);
    }

    public void eliminarRegistroProductoEstablecimiento(long idProducto, long idEstablecimiento) {
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId(idProducto,idEstablecimiento);
        registroProductoEstablecimientoRepository.deleteById(id);
    }
}
