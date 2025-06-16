package com.bromatologia.backend.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProductoEstablecimientoId implements Serializable {

    private long registroProductoId;
    private long registroEstablecimientoId;
}
