package com.bromatologia.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroProductoEstablecimientoDTO {

    @NotNull(message = "El id de registro producto no puede ser nulo")
    private long idRegistroProducto;
    @NotNull(message = "El id de registro establecimiento no puede ser nulo")
    private long idRegistroEstablecimiento;

    private RegistroProductoDTO registroProducto;
    @NotNull(message = "El numero de RNPA es obligatorio")
    @Pattern(regexp = "\\d{1,10}", message = "El numero de RNPA debe contener solo digitos ")
    private String rnpaActual;
    @NotNull(message = "La fecha de emision es obligatoria")
    private LocalDate fechaEmision;
    @NotNull(message = "El numero de RNPA Anterior es obligatorio")
    @Pattern(regexp = "\\d{1,10}", message = "El numero de RNPA debe contener solo digitos ")
    private String rnpaAnterior;
    @NotBlank(message = "El tipo de registro es obligatorio")
    private String tipo;
    @NotBlank(message = "El numero de RNE es obligatorio")
    @Pattern(regexp = "\\d{1,10}", message = "El numero de RNE debe contener solo digitos ")
    private String nroRne;
    @NotBlank(message = "El certificado no puede estar vacio")
    private String certificado;
    @NotNull(message = "El numero de expediente no puede ser nulo")
    private long expediente;
}
