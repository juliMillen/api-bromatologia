package com.bromatologia.backend.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TitularDTO {

    @Min(value = 20000000000L, message = "El CUIT debe tener al menos 11 dígitos")
    private long cuitTitular;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreTitular;
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;
}
