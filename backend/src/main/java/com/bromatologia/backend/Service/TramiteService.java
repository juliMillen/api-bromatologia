package com.bromatologia.backend.Service;

import com.bromatologia.backend.Repository.ITramiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TramiteService {
    @Autowired
    private ITramiteRepository tramiteRepository;
}
