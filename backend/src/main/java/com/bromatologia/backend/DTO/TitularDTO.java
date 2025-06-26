package com.bromatologia.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TitularDTO {

    private long cuit_Titular;
    private String nombreTitular;
    private String emailTitular;
    private String telefonoTitular;
}
