package com.bromatologia.backend.DTO;

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
@AllArgsConstructor
@NoArgsConstructor
public class RegistroEstUpdateDTO {


    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    private String departamento;

    private String localidad;

    private String direccion;

    private String enlace;

    private List<CategoriaDTO> listaCategoria = new ArrayList<>();
}
