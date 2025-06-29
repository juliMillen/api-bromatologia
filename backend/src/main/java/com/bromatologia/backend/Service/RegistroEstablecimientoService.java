package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Exception.RegistroEstablecimientoException;
import com.bromatologia.backend.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroEstablecimientoService {
    @Autowired
    private IRegistroEstablecimientoRepository registroEstablecimientoRepository;

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;

    @Autowired
    private IEmpresaRepository empresaRepository;

    @Autowired
    private ITitularRepository titularRepository;

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;

    public List<RegistroEstablecimiento> obtenerEstablecimientos() {
        return registroEstablecimientoRepository.findAll();
    }

    public RegistroEstablecimiento obtenerEstablecimientoById(long id) {
       return obtenerRegistroEstablecimientoExistente(id);
    }


    public RegistroEstablecimiento guardarRegistro(RegistroEstablecimiento registro) {
        if(registro == null){
            throw new RegistroEstablecimientoException("El registro no puede ser nulo");
        }
        return registroEstablecimientoRepository.save(registro);
    }

    public void EliminarRegistro(long id) {
        if(id <= 0){
            throw new RegistroEstablecimientoException("El id es invalido");
        }
        RegistroEstablecimiento aEliminar = obtenerRegistroEstablecimientoExistente(id);
        registroEstablecimientoRepository.delete(aEliminar);

    }

    @Transactional
    public Empresa asignarEmpresa(long id, Empresa empresa){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(id);
        empresaRepository.save(empresa);
        registro.asignarEmpresa(empresa);
        return empresa;
    }

    @Transactional
    public Titular asignarTitular(long id, Titular titular){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(id);
        titularRepository.save(titular);
        registro.asignarTitular(titular);
        return titular;
    }

    @Transactional
    public Establecimiento asignarEstablecimiento(long id, Establecimiento establecimiento){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(id);
        establecimientoRepository.save(establecimiento);
        registro.asignarEstablecimiento(establecimiento);
        return establecimiento;
    }

    @Transactional
    public Mantenimiento agregarMantenimiento(long id,Mantenimiento mantenimiento){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(id);
        mantenimientoRepository.save(mantenimiento);
        registro.agregarMantenimiento(mantenimiento);
        return mantenimiento;
    }


    public RegistroEstablecimiento obtenerRegistroEstablecimientoExistente(long id){
        if(id <= 0){
            throw new RegistroEstablecimientoException("El id del registro es invalido");
        }
        return registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
    }
}
