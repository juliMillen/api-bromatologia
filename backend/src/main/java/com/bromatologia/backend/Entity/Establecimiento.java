package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
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
    private String departamento;
    private String localidad;
    private String calle;
    private String nroCalle;
}
