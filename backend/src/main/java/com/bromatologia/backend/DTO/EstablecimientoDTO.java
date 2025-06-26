package com.bromatologia.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstablecimientoDTO {

    private long id_Establecimiento;
    private String localidad;
    private String departamento;
    private String direccion;

    //Informacion de la empresa
    private EmpresaDTO empresa;

    //Informacion del o los producto
    private List<ProductoDTO> productos;
}
