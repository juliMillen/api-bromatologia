package com.bromatologia.backend.Service;

import com.bromatologia.backend.DTO.TitularUpdateDTO;
import com.bromatologia.backend.Entity.Titular;
import com.bromatologia.backend.Exception.TitularException;
import com.bromatologia.backend.Repository.ITitularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitularService {
    @Autowired
    private ITitularRepository titularRepository;

    public Titular crearTitular(Titular titular) {
        if (titular == null) {
            throw new TitularException("El titular no puede ser nulo");
        }
        return titularRepository.save(titular);
    }

    public void eliminarTitular(long cuit) {
        if(cuit <= 0) {
            throw new TitularException("El cuit no puede ser menor que 0");
        }
        Titular aEliminar = obtenerTitularExistente(cuit);
        titularRepository.delete(aEliminar);
    }


    public List<Titular> obtenerTitular() {
        return titularRepository.findAll();
    }

    public Titular obtenerTitularPorCuit(long cuit_titular) {
        return obtenerTitularExistente(cuit_titular);
    }


    public Titular actualizarTitular(long cuit, TitularUpdateDTO dto) {
        if (dto == null || cuit <= 0) {
            throw new TitularException("El titular no puede ser nulo");
        }
        Titular aActualizar = obtenerTitularExistente(cuit);
        aActualizar.setNombreTitular(dto.getNombreTitular());
        aActualizar.setEmail(dto.getEmail());
        aActualizar.setTelefono(dto.getTelefono());
        return titularRepository.save(aActualizar);
    }


    public Titular obtenerTitularExistente(long cuitTitular) {
        if (cuitTitular <= 0) {
            throw new TitularException("El cuit no puede ser menor que 0");
        }
        return titularRepository.findById(cuitTitular).orElseThrow(() -> new TitularException("No existe titular con ese cuit"));
    }
}
