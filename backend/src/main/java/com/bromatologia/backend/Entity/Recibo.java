package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Recibo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recibo", nullable = false)
    private Mantenimiento mantenimiento;

    @OneToOne(mappedBy = "recibo",fetch = FetchType.LAZY)
    private Tramite tramite;
    private Date fecha_Recibo;
    private double importe;
}
