package com.bromatologia.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Titular {

    @Id
    private long cuit_titular;

    private String nombreTitular;

    private String email;

    private String telefono;
}
