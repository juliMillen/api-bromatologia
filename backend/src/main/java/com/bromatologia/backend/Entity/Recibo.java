package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRecibo;

    @OneToOne(mappedBy = "recibo",fetch = FetchType.LAZY)
    private Tramite tramite;

    @NotNull(message = "La fecha del recibo es obligatoria")
    private LocalDate fechaRecibo;
    @NotNull(message = "El importe no puede estar vacio")
    private double importe;

    public Recibo(LocalDate fechaRecibo, double importe) {
        this.fechaRecibo = fechaRecibo;
        this.importe = importe;
    }
}
