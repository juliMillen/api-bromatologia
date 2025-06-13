package com.bromatologia.backend.Service;

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
        Titular aEliminar = titularRepository.findById(cuit).orElseThrow(() -> new TitularException("No existe titular con ese cuit"));
        titularRepository.delete(aEliminar);
    }


    public List<Titular> obtenerTitular() {
        return titularRepository.findAll();
    }

    public Titular obtenerTitularPorCuit(long cuit_titular) {

        if (cuit_titular <= 0) {
            throw new TitularException("El cuit no puede ser menor que 0");
        }
        return titularRepository.findById(cuit_titular).orElseThrow(() -> new TitularException("No existe titular con ese cuit"));
    }


    public Titular actualizarTitular(Titular titular) {
        if (titular == null) {
            throw new TitularException("El titular no puede ser nulo");
        }
        Titular aActualizar = titularRepository.findById(titular.getCuit_titular()).orElseThrow(() -> new TitularException("No existe titular con ese cuit"));
        aActualizar.setNombreTitular(titular.getNombreTitular());
        aActualizar.setEmail(titular.getEmail());
        aActualizar.setTelefono(titular.getTelefono());
        return titularRepository.save(aActualizar);
    }
}
