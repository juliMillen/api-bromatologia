package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Recibo;
import com.bromatologia.backend.Exception.ReciboException;
import com.bromatologia.backend.Repository.IReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReciboService {

    @Autowired
    private IReciboRepository reciboRepository;

    public List<Recibo> obtenerRecibos(){
        return reciboRepository.findAll();
    }

    public Recibo obtenerReciboPorId(long id){
        return obtenerReciboExistente(id);
    }

    public Recibo crearRecibo(Recibo recibo){
        if(recibo == null){
            throw new ReciboException("El recibo no puede ser nulo");
        }
        return reciboRepository.save(recibo);
    }

    public void eliminarReciboPorId(long id){

        if (id <= 0) {
            throw new ReciboException("El id del recibo no es valido");
        }
        Recibo aEliminar = obtenerReciboExistente(id);
        reciboRepository.delete(aEliminar);
    }

    public Recibo obtenerReciboExistente(long id){
        if (id <= 0) {
            throw new ReciboException("El id del recibo no es valido");
        }
        return reciboRepository.findById(id).orElseThrow(() -> new ReciboException("El recibo no se ha encontrado"));
    }
}
