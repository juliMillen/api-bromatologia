package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Titular {

    @Id
    private long cuit_titular;

    @OneToOne(mappedBy = "titular",fetch = FetchType.LAZY)
    private Empresa empresa;

    @NotBlank(message = "El nombre del titular es obligatorio")
    private String nombreTitular;

    @NotBlank(message="El email no puede ser nulo")
    private String email;

    @NotNull(message = "El telefono no puede ser nulo")
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;

    public Titular(String nombreTitular, String email, String telefono) {
        this.nombreTitular = nombreTitular;
        this.email = email;
        this.telefono = telefono;
    }
}
