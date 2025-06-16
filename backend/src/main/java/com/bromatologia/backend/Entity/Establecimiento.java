package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Establecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id",nullable = false)
    private Empresa empresa;

    @NotBlank(message = "El departamento no puede estar vacio")
    private String departamento;
    @NotBlank(message = "La localidad no puede estar vacia")
    private String localidad;
    @NotBlank(message = "La calle no puede estar vacia")
    private String calle;
    @NotBlank(message = "El numero de la calle no puede estar vacia")
    private String nroCalle;
}
