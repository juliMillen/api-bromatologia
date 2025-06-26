package com.bromatologia.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {

    private long cuit_Empresa;
    private String nombreEmpresa;

    //datos del titular
    private TitularDTO titular;
    private String email;
    private String telefono;

    //establecimiento

    private List<EstablecimientoDTO> establecimientos;
}
