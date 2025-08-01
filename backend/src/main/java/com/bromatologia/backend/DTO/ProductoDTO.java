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
public class ProductoDTO {
    @NotNull(message = "El id no puede ser nulo")
    private long idProducto;
    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    @NotBlank(message = "La denominacion es obligatoria")
    private String denominacion;
    @NotBlank(message = "El nombre de fantasia es obligatorio")
    private String nombreFantasia;
}
