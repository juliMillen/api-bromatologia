package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Tramite;
import com.bromatologia.backend.Exception.TramiteException;
import com.bromatologia.backend.Repository.ITramiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TramiteService {
    @Autowired
    private ITramiteRepository tramiteRepository;

    public List<Tramite> obtenerTramites() {
        return tramiteRepository.findAll();
    }

    public Tramite obtenerTramitePorId(long id){
        if(id <= 0){
            throw new TramiteException("El id del tramite no es valido");
        }
        return tramiteRepository.findById(id).orElseThrow(() -> new TramiteException("El tramite buscado no se ha encontrado"));
    }

    public Tramite crearTramite(Tramite tramite) {
        if(tramite == null){
            throw new TramiteException("El tramite no puede ser null");
        }
        return tramiteRepository.save(tramite);
    }

    public void eliminarTramite(long id) {
        if(id <= 0){
            throw new TramiteException("El id del tramite no es valido");
        }
        Tramite aElimar = tramiteRepository.findById(id).orElseThrow(() -> new TramiteException("El tramite no se ha encontrado"));
        tramiteRepository.delete(aElimar);
    }
}
