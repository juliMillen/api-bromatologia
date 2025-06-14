package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Establecimiento;
import com.bromatologia.backend.Exception.EstablecimientoException;
import com.bromatologia.backend.Repository.IEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoService {

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;


    public List<Establecimiento> obtenerEstablecimientos(){
        return establecimientoRepository.findAll();
    }

    public Establecimiento obtenerEstablecimientoPorId(long id){
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

    public void eliminarEstablecimiento(long id){
        if(id <= 0){
            throw new EstablecimientoException("Id invalido");
        }
        Establecimiento aEliminar = establecimientoRepository.findById(id).orElseThrow(() -> new EstablecimientoException("Establecimiento no encontrado"));
        establecimientoRepository.delete(aEliminar);
    }
}

