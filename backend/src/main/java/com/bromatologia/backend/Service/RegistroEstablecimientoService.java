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
        if(id <= 0){
            throw new RegistroEstablecimientoException("El id del registro es invalido");
        }
        return registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
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
        RegistroEstablecimiento aEliminar = registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha podido eliminar"));
        registroEstablecimientoRepository.delete(aEliminar);

    }

    @Transactional
    public Empresa asignarEmpresa(Long id, Empresa empresa){
        RegistroEstablecimiento registro = registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
        empresaRepository.save(empresa);
        registro.asignarEmpresa(empresa);
        return empresa;
    }

    @Transactional
    public Titular asignarTitular(Long id, Titular titular){
        RegistroEstablecimiento registro = registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
        titularRepository.save(titular);
        registro.asignarTitular(titular);
        return titular;
    }

    @Transactional
    public Establecimiento asignarEstablecimiento(Long id, Establecimiento establecimiento){
        RegistroEstablecimiento registro = registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
        establecimientoRepository.save(establecimiento);
        registro.asignarEstablecimiento(establecimiento);
        return establecimiento;
    }

    @Transactional
    public Mantenimiento agregarMantenimiento(Long id,Mantenimiento mantenimiento){
        RegistroEstablecimiento registro = registroEstablecimientoRepository.findById(id).orElseThrow(() -> new RegistroEstablecimientoException("El id no se ha encontrado"));
        mantenimientoRepository.save(mantenimiento);
        registro.agregarMantenimiento(mantenimiento);
        return mantenimiento;
    }
}
