package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    @Autowired
    private IProductoRepository productoRepository;
}
