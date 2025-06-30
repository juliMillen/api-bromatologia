package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.*;
import com.bromatologia.backend.Exception.*;
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

    public void eliminarRegistro(long id) {
        if(id <= 0){
            throw new RegistroEstablecimientoException("El id es invalido");
        }
        RegistroEstablecimiento aEliminar = obtenerRegistroEstablecimientoExistente(id);
        registroEstablecimientoRepository.delete(aEliminar);
    }

    @Transactional
    public Empresa asignarEmpresa(long idRegistroEstablecimiento, long cuitEmpresa){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(idRegistroEstablecimiento);
        Empresa empresa = empresaRepository.findById(cuitEmpresa).orElseThrow(() -> new EmpresaException("El empresa no existe"));
        registro.asignarEmpresa(empresa);
        return empresa;
    }

    @Transactional
    public Titular asignarTitular(long idRegistroEstablecimiento, long cuitTitular){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(idRegistroEstablecimiento);
        Titular titular = titularRepository.findById(cuitTitular).orElseThrow(() -> new TitularException("No existe el titular registro"));
        registro.asignarTitular(titular);
        return titular;
    }

    @Transactional
    public Establecimiento asignarEstablecimiento(long idRegistroEstablecimiento, long idEstablecimiento){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(idRegistroEstablecimiento);
        Establecimiento establecimiento = establecimientoRepository.findById(idEstablecimiento).orElseThrow(() -> new EstablecimientoException("El establecimiento no se encontro"));
        registro.asignarEstablecimiento(establecimiento);
        return establecimiento;
    }

    @Transactional
    public Mantenimiento agregarMantenimiento(long idRegistroEstablecimiento,long idMantenimiento){
        RegistroEstablecimiento registro = obtenerRegistroEstablecimientoExistente(idRegistroEstablecimiento);
        Mantenimiento mantenimiento = mantenimientoRepository.findById(idMantenimiento).orElseThrow(() -> new MantenimientoException("no se ha encontrado el mantenimiento"));
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
