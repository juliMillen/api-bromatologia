package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.IRegistroProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroProductoService {
    @Autowired
    private IRegistroProductoRepository registroProductoRepository;
}
