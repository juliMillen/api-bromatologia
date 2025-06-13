package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {
    @Autowired
    private IEmpresaRepository empresaRepository;
}
