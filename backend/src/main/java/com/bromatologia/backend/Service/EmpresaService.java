package com.bromatologia.backend.Service;

import com.bromatologia.backend.DTO.EmpresaUpdateDTO;
import com.bromatologia.backend.Entity.Empresa;
import com.bromatologia.backend.Exception.EmpresaException;
import com.bromatologia.backend.Repository.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private IEmpresaRepository empresaRepository;


    public List<Empresa> obtenerEmpresas() {
        return empresaRepository.findAll();
    }


    public Empresa obtenerEmpresaPorId(long id){
        return obtenerEmpresaExistente(id);
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
        Empresa aEliminar = obtenerEmpresaExistente(id);
        empresaRepository.delete(aEliminar);
    }

    public Empresa actualizarEmpresa(long cuit, EmpresaUpdateDTO dto) {
        if (dto == null || cuit <= 0) {
            throw new EmpresaException("Empresa no encontrada o cuit invalido");
        }
        Empresa aActualizar = obtenerEmpresaExistente(cuit);
        aActualizar.setEmail(dto.getEmail());
        aActualizar.setTelefono(dto.getTelefono());
        aActualizar.setDepartamento(dto.getDepartamento());
        aActualizar.setLocalidad(dto.getLocalidad());
        aActualizar.setDireccion(dto.getDireccion());
        aActualizar.setPassword(dto.getPassword());

        return empresaRepository.save(aActualizar);
    }


    public Empresa obtenerEmpresaExistente(long cuitEmpresa){
        if(cuitEmpresa <= 0){
            throw new EmpresaException("Id invalido");
        }
        return empresaRepository.findById(cuitEmpresa).orElseThrow(() -> new EmpresaException("Empresa invalida"));
    }

}
