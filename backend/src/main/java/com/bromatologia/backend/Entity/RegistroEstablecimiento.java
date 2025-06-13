package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
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
    private long id_registro_establecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_titular", nullable = false)
    private Titular titular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "registroEstablecimiento", fetch = FetchType.LAZY)
    private List<Mantenimiento> mantenimiento = new ArrayList<>();

    @ManyToMany(mappedBy = "establecimientos",fetch = FetchType.LAZY)
    private List<RegistroProducto> registroProductos;

    private String categoriaAnt;

    private double arancel;

    private Date fechaEmision;

    private Date fechaVencimiento;

    private String estado;


}
