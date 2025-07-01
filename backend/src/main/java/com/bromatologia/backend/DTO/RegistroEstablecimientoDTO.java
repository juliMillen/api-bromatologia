package com.bromatologia.backend.DTO;
import jakarta.validation.constraints.NotBlank;
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
public class RegistroEstablecimientoDTO {

    @NotNull(message = "El registro no puede ser nulo")
    private long idRegistroEstablecimiento;
    @NotNull(message = "El cuit de la empresa es obligatorio")
    private long cuitEmpresa;
    @NotNull(message = "El cuit del titular es obligatorio")
    private long cuitTitular;
    @NotNull(message = "El id del establecimiento es obligatorio")
    private long idEstablecimiento;

    private List<MantenimientoDTO> mantenimientos = new ArrayList<>();
    @NotBlank(message = "La categoria Anterior es obligatorio")
    private String categoriaAnt;
    @NotNull(message = "El arancel no puede ser nulo")
    private double arancel;
    @NotNull(message = "La fecha de emision no puede ser nulo")
    private LocalDate fechaEmision;
    @NotNull(message = "La fecha de vencimiento no puede ser nulo")
    private LocalDate fechaVencimiento;
    @NotBlank(message = "El estado del registro es obligatorio")
    private String estado;
}
