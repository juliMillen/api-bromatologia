package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroEstablecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_registroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_titular", nullable = false)
    private Titular titular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "registroEstablecimiento", fetch = FetchType.LAZY)
    private List<Mantenimiento> mantenimiento = new ArrayList<>();

    @OneToMany(mappedBy = "registroEstablecimiento", fetch = FetchType.LAZY)
    private List<RegistroProducto> registroProductos = new ArrayList<>();

    @NotBlank(message = "la categoria anterior no puede estar vacia")
    private String categoriaAnt;

    @NotNull(message = "El arancel no puede estar vacio")
    private double arancel;

    @NotNull(message = "La fecha de emision es obligatoria")
    private Date fechaEmision;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private Date fechaVencimiento;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;

}
