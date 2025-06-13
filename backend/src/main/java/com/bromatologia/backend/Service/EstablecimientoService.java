package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.IEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstablecimientoService {

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;
}
