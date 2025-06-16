package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Titular {

    @Id
    private long cuit_titular;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuit_empresa")
    private Empresa empresa;

    @NotBlank(message = "El nombre del titular es obligatorio")
    private String nombreTitular;

    @NotBlank(message="El email no puede ser nulo")
    private String email;

    @NotNull(message = "El telefono no puede ser nulo")
    private long telefono;
}
