package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Empresa {
    @Id
    @NotNull
    @Min(value = 20000000000L, message = "El CUIT debe tener al menos 11 dígitos")
    private long cuitEmpresa;

    @NotBlank
    private String razonSocial;

    @NotNull
    private LocalDate fechaAlta;

    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;

    @NotBlank
    private String departamento;

    @NotBlank
    private String localidad;

    @NotBlank
    private String direccion;

    @NotBlank
    private String password;

    public Empresa(long cuitEmpresa,String razonSocial,LocalDate fechaAlta, String email, String telefono, String departamento, String localidad, String direccion, String password) {
        this.cuitEmpresa = cuitEmpresa;
        this.razonSocial = razonSocial;
        this.fechaAlta = fechaAlta;
        this.email = email;
        this.telefono = telefono;
        this.departamento = departamento;
        this.localidad = localidad;
        this.direccion = direccion;
        this.password = password;
    }

}
