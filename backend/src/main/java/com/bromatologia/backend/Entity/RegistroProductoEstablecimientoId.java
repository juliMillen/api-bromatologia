package com.bromatologia.backend.Entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProductoEstablecimientoId implements Serializable {

    private long registroProductoId;
    private long registroEstablecimientoId;
}
