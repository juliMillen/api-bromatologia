package com.bromatologia.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RegistroProductoDTO {

    private long idRegistroProducto;
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "El id no puede ser nulo")
    private long idProducto;

    @NotBlank(message = "El elaborador es obligatorio")
    private String elaborador;

    private List<MantenimientoDTO> mantenimientos = new ArrayList<>();
}
