package com.bromatologia.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    private long idCategoria;
    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String nombre;

    private long idRubro;
    private long idActividad;

    public CategoriaDTO(String nombre) {
        this.nombre = nombre;
    }

}
