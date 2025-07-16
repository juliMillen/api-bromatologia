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
public class EstablecimientoDTO {

    private long idEstablecimiento;
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;
    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    //Informacion de la empresa
    private long cuitEmpresa;

    //Informacion del o los producto
    private List<ProductoDTO> productos = new ArrayList<>();
}
