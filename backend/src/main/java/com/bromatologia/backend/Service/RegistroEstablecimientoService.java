package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.IRegistroEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroEstablecimientoService {
    @Autowired
    private IRegistroEstablecimientoRepository registroEstablecimientoRepository;
}
