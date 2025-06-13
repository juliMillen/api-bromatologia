package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.IReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReciboService {

    @Autowired
    private IReciboRepository reciboRepository;
}
