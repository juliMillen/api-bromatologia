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
public class RubroDTO {

    private long idRubro;
    @NotBlank(message = "El nombre del rubro es obligatorio")
    private String nombreRubro;
    private List<CategoriaDTO> listaDTO = new ArrayList<>();

    public RubroDTO(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }
}
