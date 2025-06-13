package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Mantenimiento;
import com.bromatologia.backend.Exception.MantenimientoException;
import com.bromatologia.backend.Repository.IMantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    public List<Mantenimiento> obtenerMantenimientos(){
        return mantenimientoRepository.findAll();
    }

    public Mantenimiento obtenerMantenimiento(Long id){
        if (id <= 0) {
            throw new MantenimientoException("El id del mantenimiento no es valido");
        }
        return mantenimientoRepository.findById(id).orElseThrow(() -> new MantenimientoException("El id no existe"));

    }

    public Mantenimiento registrarMantenimiento(Mantenimiento mantenimiento) {
        if (mantenimiento == null) {
            throw new MantenimientoException("El mantenimiento no es valido");
        }
        return mantenimientoRepository.save(mantenimiento);
    }


    public void eliminarMantenimiento(Long id) {
        if (id <= 0) {
          throw new MantenimientoException("id invalido");
        }
        Mantenimiento aEliminar = mantenimientoRepository.findById(id).orElseThrow(() -> new MantenimientoException("El id no existe"));
        mantenimientoRepository.delete(aEliminar);
    }
}
