package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tramite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tramite;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recibos", nullable = false)
    private Recibo recibo;
}
