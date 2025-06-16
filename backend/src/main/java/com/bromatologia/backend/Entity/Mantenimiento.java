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
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_mantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_establecimiento", nullable = false)
    private RegistroEstablecimiento registroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_producto", nullable = false)
    private RegistroProducto registroProducto;

    @OneToMany(mappedBy = "mantenimiento")
    private List<Recibo> recibos = new ArrayList<>();

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private Date fecha_mantenimiento;
    @NotBlank(message = "El tramite asociado es obligatorio")
    private String tramiteAsociado;

    private String enlaceRecibido;

}
