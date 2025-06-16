package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.RegistroEstablecimiento;
import com.bromatologia.backend.Exception.RegistroEstablecimientoException;
import com.bromatologia.backend.Repository.IRegistroEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroEstablecimientoService {
    @Autowired
    private IRegistroEstablecimientoRepository registroEstablecimientoRepository;

    public List<RegistroEstablecimiento> obtenerEstablecimientos() {
        return registroEstablecimientoRepository.findAll();
    }

    public RegistroEstablecimiento obtenerEstablecimientoById(long id) {
        if(id <= 0){
            throw new RegistroEstablecimientoException("El id del registro es invalido");
        }
        return registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
    }

    public RegistroEstablecimiento guardarRegistro(RegistroEstablecimiento registro) {
        if(registro == null){
            throw new RegistroEstablecimientoException("El registro no puede ser nulo");
        }
        return registroEstablecimientoRepository.save(registro);
    }

    public void EliminarRegistro(long id) {
        if(id <= 0){
            throw new RegistroEstablecimientoException("El id es invalido");
        }
        RegistroEstablecimiento aEliminar = registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha podido eliminar"));
        registroEstablecimientoRepository.delete(aEliminar);

    }
}
