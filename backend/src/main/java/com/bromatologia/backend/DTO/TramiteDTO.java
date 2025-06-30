package com.bromatologia.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TramiteDTO {

    @NotNull(message = "El id no puede ser nulo")
    private long idTramite;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreTramite;

    //Recibo
    @NotNull(message = "El recibo es obligatorio y no puede ser nulo")
    private ReciboDTO recibo;
}
