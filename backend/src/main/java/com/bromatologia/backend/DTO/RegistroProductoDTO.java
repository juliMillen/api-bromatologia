package com.bromatologia.backend.DTO;

import com.bromatologia.backend.Exception.MantenimientoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class RegistroProductoDTO {


    @NotBlank(message = "El RPPA es obligatorio")
    @Pattern(regexp = "08[-/]\\d{6}", message = "El RPPA debe tener el formato 08-000000 o 08/000000")
    private String rppa;

    @NotNull(message = "La fecha de emision es obligatoria")
    private LocalDate fechaEmision;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    private RegistroEstablecimientoDTO registroEstablecimiento;

    @NotBlank(message = "La denominacion es obligatoria")
    private String denominacion;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El nombre de fantasia es obligatorio")
    private String nombreFantasia;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoriaProducto;

    @NotNull(message = "El numero de expediente es obligatorio")
    private long expediente;

    @NotBlank(message = "El enlace es obligatorio")
    private String enlace;

    @NotBlank(message = "El elaborador es obligatorio")
    private String elaborador;

    private List<MantenimientoDTO> mantenimientos = new ArrayList<>();
}
