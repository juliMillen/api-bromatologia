package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Mantenimiento;
import com.bromatologia.backend.Entity.Tramite;
import com.bromatologia.backend.Exception.MantenimientoException;
import com.bromatologia.backend.Repository.IMantenimientoRepository;
import com.bromatologia.backend.Repository.ITramiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    @Autowired
    private ITramiteRepository tramiteRepository;

    public List<Mantenimiento> obtenerMantenimientos(){
        return mantenimientoRepository.findAll();
    }

    public Mantenimiento obtenerMantenimiento(long id){
        return obtenerMantenimientoExistente(id);
    }

    public Mantenimiento registrarMantenimiento(Mantenimiento mantenimiento) {
        if (mantenimiento == null) {
            throw new MantenimientoException("El mantenimiento no es valido");
        }
        return mantenimientoRepository.save(mantenimiento);
    }


    public void eliminarMantenimiento(long id) {
        if (id <= 0) {
          throw new MantenimientoException("id invalido");
        }
        Mantenimiento aEliminar = obtenerMantenimientoExistente(id);
        mantenimientoRepository.delete(aEliminar);
    }

    @Transactional
    public Tramite agregarTramite(long id, Tramite tramite){
        Mantenimiento mantenimiento = obtenerMantenimientoExistente(id);
        tramite.setMantenimiento(mantenimiento);
        mantenimiento.agregarTramite(tramite);
        tramiteRepository.save(tramite);
        return tramite;
    }

    public Mantenimiento obtenerMantenimientoExistente(long id_mantenimiento){
        if (id_mantenimiento <= 0) {
            throw new MantenimientoException("El id del mantenimiento no es valido");
        }
        return mantenimientoRepository.findById(id_mantenimiento).orElseThrow(() -> new MantenimientoException("El id no existe"));
    }
}
