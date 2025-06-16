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

    @NotNull(message = "El numero de RNPA es obligatorio")
    private long nroRnpaActual;

    @NotBlank(message = "El tipo no puede estar vacio")
    private String tipo;

    @NotNull(message = "La fecha de emision es obligatoria")
    private Date fecha_Emision;

    @NotNull(message = "El numero anterior de RNPA es obligatorio")
    private long nroAnteriorRnpa;

    private String certificado;

    private long expediente;
}
