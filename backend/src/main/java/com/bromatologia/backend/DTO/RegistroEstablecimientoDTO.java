package com.bromatologia.backend.DTO;
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
public class RegistroEstablecimientoDTO {

    @NotNull(message = "El registro no puede ser nulo")
    @Pattern(regexp = "08[-/]\\d{6}", message = "El RPE debe tener el formato 08-000000 o 08/000000")
    private String rpe;

    @NotNull(message = "La fecha de emision no puede ser null")
    private LocalDate fechaEmision;

    @NotNull(message = "La fecha de vencimiento no puede ser null")
    private LocalDate fechaVencimiento;

    private EmpresaDTO empresa;

    @NotBlank(message = "El departamento del establecimiento es obligatorio")
    private String departamento;

    @NotBlank(message = "La localidad del establecimiento es obligatoria")
    private String localidad;

    @NotBlank(message = "La direccion del establecimiento es obligatoria")
    private String direccion;

    @NotNull(message = "El numero de expediente es obligatorio")
    private long expediente;

    @NotBlank(message = "El enlace es obligatorio")
    private String enlace;

    private List<CategoriaDTO> categorias = new ArrayList<>();

    private List<MantenimientoDTO> mantenimientos = new ArrayList<>();
}
