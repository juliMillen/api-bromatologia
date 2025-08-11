package com.bromatologia.backend.DTO;

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
public class RegistroProdUpdateDTO {

    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String denominacion;
    private String marca;
    private String nombreFantasia;
    private String categoriaProducto;
    private String enlace;
    private long expediente;
    private String elaborador;
    private List<CategoriaDTO> listaCategoria = new ArrayList<>();
}
