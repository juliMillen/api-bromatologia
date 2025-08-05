package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Actividad;
import com.bromatologia.backend.Entity.Categoria;
import com.bromatologia.backend.Exception.CategoriaException;
import com.bromatologia.backend.Repository.IActividadRepository;
import com.bromatologia.backend.Repository.ICategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private IActividadRepository actividadRepository;

    public List<Categoria> obtenerCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria obtenerCategoriaPorId(long idCategoria){
        if(idCategoria <= 0){
            throw new CategoriaException("El id del categoria no es valido");
        }
        return obtenerCategoriaExistente(idCategoria);
    }


    public Categoria obtenerCategoriaPorNombre(String nombreCategoria){
        return categoriaRepository.findByNombreCategoria(nombreCategoria);
    }

    public Categoria registrarCategoria(Categoria categoria){
        if(categoria == null){
            throw new CategoriaException("La categoria es null");
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Actividad asignarActividad(long idCategoria, long idActividad){
        Categoria categoria = obtenerCategoriaExistente(idCategoria);
        Actividad actividad= actividadRepository.findById(idActividad).orElseThrow(() -> new CategoriaException("El id de la actividad no existe"));
        categoria.agregarActividad(actividad);
        return actividad;
    }

    public void eliminarCategoria(long idCategoria){
        if(idCategoria <= 0){
            throw new CategoriaException("El id del categoria no es valido");
        }
        Categoria aEliminar = obtenerCategoriaPorId(idCategoria);
        categoriaRepository.delete(aEliminar);
    }


    public Categoria obtenerCategoriaExistente(long idCategoria){
        if(idCategoria <= 0){
            throw new CategoriaException("El id del categoria no es valido");
        }
        return categoriaRepository.findById(idCategoria).orElseThrow(() -> new CategoriaException("El id del categoria no existe"));
    }
}
