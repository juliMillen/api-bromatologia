package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "registro_producto_establecimiento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroProductoEstablecimiento {

    @EmbeddedId
    private RegistroProductoEstablecimientoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("registroProductoId")
    @JoinColumn(name = "registro_producto_id")
    private RegistroProducto registroProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("registroEstablecimientoId")
    @JoinColumn(name = "registro_establecimiento_id")
    private RegistroEstablecimiento registroEstablecimiento;

    //solo datos pertenecientes a la relacion
    @NotBlank(message = "El numero de RNPA es obligatorio")
    @Pattern(regexp = "\\d{1,10}", message = "El numero de RNPA debe contener solo digitos ")
    private String rnpaActual;

    @NotNull(message = "La fecha de emision es obligatoria")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaDeEmision;

    @NotBlank(message = "El numero anterior de RNPA es obligatorio")
    @Pattern(regexp = "\\d{1,10}", message = "El numero de RNPA debe contener solo digitos ")
    private String rnpaAnterior;

    @NotBlank(message = "El tipo no puede estar vacio")
    private String tipo;

    @NotBlank(message = "El numero de RNE es obligatorio")
    @Pattern(regexp = "\\d{1,10}", message = "El numero de RNE debe contener solo digitos ")
    private String nroRne;

    //certificado emitido
    private String certificado;

    //numero de expediente
    private long expediente;
}
