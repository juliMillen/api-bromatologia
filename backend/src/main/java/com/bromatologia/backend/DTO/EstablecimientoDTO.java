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


}
