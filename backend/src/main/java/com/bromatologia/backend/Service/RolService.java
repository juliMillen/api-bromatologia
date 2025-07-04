package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Rol;
import com.bromatologia.backend.Exception.RolException;
import com.bromatologia.backend.Repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RolService {
    @Autowired
    private IRolRepository rolRepository;

    public Rol obtenerRolPorTipo(String tipo) {
       return rolRepository.findByTipoRol(tipo).orElseThrow(() -> new RolException("Rol: "+ tipo + "no existe"));
    }
}
