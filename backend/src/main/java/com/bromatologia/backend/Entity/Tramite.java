package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tramite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tramite;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recibo", nullable = false)
    private Recibo recibo;
}
