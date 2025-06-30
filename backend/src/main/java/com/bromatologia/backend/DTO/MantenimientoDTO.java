package com.bromatologia.backend.DTO;

import com.bromatologia.backend.Entity.Tramite;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MantenimientoDTO {

    @NotNull(message = "El id no puede ser nulo")
    private long idMantenimiento;
    @NotNull(message = "La fecha no puede ser nulo")
    private LocalDate fechaMantenimiento;

    private String enlaceRecibido;

    //Datos del tramite
    private List<TramiteDTO> tramites = new ArrayList<>();
}
