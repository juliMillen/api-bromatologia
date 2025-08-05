package com.bromatologia.backend.DTO;

import com.bromatologia.backend.Entity.Actividad;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDTO {

    private long idActividad;
    @NotBlank(message = "El nombre de la actividad es obligatorio")
    private String nombreActividad;

    private long idCategoria;

    public ActividadDTO(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }
}
