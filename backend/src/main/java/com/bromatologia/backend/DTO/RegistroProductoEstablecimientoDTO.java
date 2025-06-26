package com.bromatologia.backend.DTO;

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

    private long idProducto;
    private long idEstablecimiento;

    private long rnpaActual;
    private LocalDate fechaDeEmision;
    private long rnpaAnterior;
    private String tipo;
    private long nroRne;
    private String certificado;
    private long expediente;
}
