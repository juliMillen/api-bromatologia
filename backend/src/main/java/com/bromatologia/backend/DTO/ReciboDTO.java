package com.bromatologia.backend.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReciboDTO {
    @NotNull(message = "El id no puede ser nulo")
    private long idRecibo;
    @NotNull(message = "La fecha del recibo no puede ser nulo")
    private LocalDate fechaRecibo;
    @NotNull(message = "El importe no puede ser nulo")
    private double importe;
}
