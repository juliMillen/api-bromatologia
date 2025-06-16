package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.RegistroProductoEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimientoId;
import com.bromatologia.backend.Exception.RegistroProductoEstablecimientoException;
import com.bromatologia.backend.Repository.IRegistroProductoEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroProductoEstablecimientoService {
    @Autowired
    private IRegistroProductoEstablecimientoRepository registroProductoEstablecimientoRepository;

    public List<RegistroProductoEstablecimiento> obtenerRegistroProductoEstablecimiento() {
        return registroProductoEstablecimientoRepository.findAll();
    }

    public RegistroProductoEstablecimiento obtenerRegistroProductoEstablecimientoPorId(long idProducto, long idEstablecimiento) {
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId(idProducto,idEstablecimiento);
        return registroProductoEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroProductoEstablecimientoException("El registro del establecimiento no existe"));
    }

    public RegistroProductoEstablecimiento crearRegistroProductoEstablecimeinto(RegistroProductoEstablecimiento reg) {
        return registroProductoEstablecimientoRepository.save(reg);
    }

    public void eliminarRegistroProductoEstablecimiento(long idProducto, long idEstablecimiento) {
        RegistroProductoEstablecimientoId id = new RegistroProductoEstablecimientoId(idProducto,idEstablecimiento);
        registroProductoEstablecimientoRepository.deleteById(id);
    }
}
