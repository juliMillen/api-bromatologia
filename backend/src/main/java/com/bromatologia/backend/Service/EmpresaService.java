package com.bromatologia.backend.Service;

import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Exception.EmpresaException;
import com.bromatologia.backend.Repository.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private IEmpresaRepository empresaRepository;

    public List<Empresa> obtenerEmpresas() {
        return empresaRepository.findAll();
    }


    public Empresa obtenerEmpresaPorId(long id){
        if(id <= 0){
            throw new EmpresaException("Id invalido");
        }

        return empresaRepository.findById(id).orElseThrow(() -> new EmpresaException("Empresa no encontrada"));
    }


    public Empresa crearEmpresa(Empresa empresa){
        if(empresa == null){
            throw new EmpresaException("Los datos de la empresa no pueden ser nulos");
        }
        return empresaRepository.save(empresa);
    }

    public void eliminarEmpresaPorId(long id){
        if(id <= 0){
            throw new EmpresaException("Id invalido");
        }
        Empresa aEliminar = empresaRepository.findById(id).orElseThrow(() -> new EmpresaException("Empresa no encontrada"));
        empresaRepository.delete(aEliminar);
    }

    public Empresa actualizarEmpresa(Long cuit,Empresa empresa) {
        if (empresa == null || cuit <= 0) {
            throw new EmpresaException("Empresa no encontrada o cuit invalido");
        }
        Empresa aActualizar = empresaRepository.findById(cuit).orElseThrow(() -> new EmpresaException("Empresa no encontrada"));
        aActualizar.setEmail(empresa.getEmail());
        aActualizar.setTelefono(empresa.getTelefono());

        return empresaRepository.save(aActualizar);
    }



}
