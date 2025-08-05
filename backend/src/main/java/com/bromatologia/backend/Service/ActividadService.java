package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Actividad;
import com.bromatologia.backend.Entity.Categoria;
import com.bromatologia.backend.Entity.Rubro;
import com.bromatologia.backend.Exception.CategoriaException;
import com.bromatologia.backend.Repository.IActividadRepository;
import com.bromatologia.backend.Repository.ICategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private IActividadRepository actividadRepository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    public List<Actividad>  obtenerActividades() {
        return actividadRepository.findAll();
    }

    public Actividad obtenerActividad(long id) {
        if(id <= 0){
            throw new IllegalArgumentException("El id de la actividad no puede ser negativo");
        }
        return obtenerActividadExistente(id);
    }

    public Actividad crearActividad(Actividad actividad) {
        if(actividad == null){
            throw new IllegalArgumentException("La actividad no puede ser nulo");
        }
        return actividadRepository.save(actividad);
    }

    public void eliminarActividad(long id) {
        if(id <= 0){
            throw new IllegalArgumentException("El id de la actividad no puede ser negativo");
        }
        Actividad aEliminar = obtenerActividadExistente(id);
        actividadRepository.delete(aEliminar);
    }

    public Actividad obtenerActividadExistente(long id){
        if(id <= 0){
            throw new IllegalArgumentException("El id del actividad no puede ser negativo");
        }
        return actividadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("La actividad no existe"));
    }
}
