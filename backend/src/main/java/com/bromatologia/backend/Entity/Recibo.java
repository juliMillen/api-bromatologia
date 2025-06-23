package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Recibo;

    @OneToOne(mappedBy = "recibo",fetch = FetchType.LAZY)
    private Tramite tramite;

    @NotNull(message = "La fecha del recibo es obligatoria")
    private Date fecha_Recibo;
    @NotNull(message = "El importe no puede estar vacio")
    private double importe;

    public Recibo(Date fecha_Recibo, double importe) {
        this.fecha_Recibo = fecha_Recibo;
        this.importe = importe;
    }
}
