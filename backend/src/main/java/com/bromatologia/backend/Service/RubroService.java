package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Rubro;
import com.bromatologia.backend.Repository.IRubroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RubroService {

    @Autowired
    private IRubroRepository rubroRepository;


    public List<Rubro> obtenerRubros() {
        return rubroRepository.findAll();
    }


    public Rubro obtenerRubrosPorIdRubro(long idRubro) {
        if(idRubro <= 0){
            throw new IllegalArgumentException("Id de rubro es menor o igual a 0");
        }
        return obtenerRubroExistente(idRubro);

    }


    public Rubro crearRubro(Rubro rubro) {
        if(rubro == null){
        throw new IllegalArgumentException("El rubro no puede ser nulo");
        }
        return rubroRepository.save(rubro);
    }


    public void eliminarRubro(long idRubro) {
        if(idRubro <= 0){
            throw new IllegalArgumentException("Id de rubro es menor o igual a 0");
        }
        Rubro aEliminar = obtenerRubroExistente(idRubro);
        rubroRepository.delete(aEliminar);
    }

    public Rubro obtenerRubroExistente(long idRubro) {
        if(idRubro <= 0){
            throw new IllegalArgumentException("Id de rubro es menor o igual a 0");
        }
        return rubroRepository.findById(idRubro).orElseThrow(() -> new IllegalArgumentException("Id de rubro no encontrado"));
    }
}
