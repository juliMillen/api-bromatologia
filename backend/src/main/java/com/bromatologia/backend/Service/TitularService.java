package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.ITitularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitularService {
    @Autowired
    private ITitularRepository titularRepository;
}
