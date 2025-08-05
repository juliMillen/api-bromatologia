package com.bromatologia.backend.DTO;

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
public class CategoriaDTO {

    private long idCategoria;
    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String nombreCategoria;

    private long idRubro;

    private List<ActividadDTO> listaActividades = new ArrayList<>();

    public CategoriaDTO(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

}
