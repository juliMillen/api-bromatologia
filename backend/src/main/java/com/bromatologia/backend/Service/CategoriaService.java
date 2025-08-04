package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Categoria;
import com.bromatologia.backend.Exception.CategoriaException;
import com.bromatologia.backend.Repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

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
