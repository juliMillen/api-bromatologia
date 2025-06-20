package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Recibo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mantenimiento", nullable = false)
    private Mantenimiento mantenimiento;

    @OneToOne(mappedBy = "recibo",fetch = FetchType.LAZY)
    private Tramite tramite;

    @NotNull(message = "La fecha del recibo es obligatoria")
    private Date fecha_Recibo;
    @NotNull(message = "El importe no puede estar vacio")
    private double importe;
}
