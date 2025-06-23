package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tramite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tramite;

    @NotBlank(message = "El nombre del tramite es obligatorio")
    private String nombreTramite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mantenimiento", nullable = false)
    private Mantenimiento mantenimiento;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_recibo", nullable = false, unique = true)
    private Recibo recibo;

    public Tramite(String nombreTramite, Recibo recibo){
        this.nombreTramite = nombreTramite;
        this.recibo = recibo;
    }
}
