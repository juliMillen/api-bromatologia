package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.IMantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MantenimientoService {

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;
}
