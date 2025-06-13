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
public class RegistroProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_registroProducto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToMany(mappedBy = "registroProducto",fetch = FetchType.LAZY)
    private List<Mantenimiento> mantenimiento = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "registro_producto_Establecimiento",
            joinColumns = @JoinColumn(name = "registro_producto_id"),
            inverseJoinColumns = @JoinColumn(name = "registro_establecimiento_id")
    )
    private List<RegistroEstablecimiento> registrosEstablecimientos = new ArrayList<>();

    private long nroRnpaActual;

    private String tipo;

    private Date fecha_Emision;

    private long nroAnteriorRnpa;

    private String certificado;

    private long expediente;
}
