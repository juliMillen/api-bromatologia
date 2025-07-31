package com.bromatologia.backend.DTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class EmpresaDTO {

    @NotNull(message = "El cuit no puede ser nulo")
    @Min(value = 20000000000L, message = "El CUIT debe tener al menos 11 dígitos")
    private long cuitEmpresa;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreEmpresa;

    @NotBlank(message = "La razon social es obligatoria")
    private String razonSocial;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;

    @NotBlank(message = "El departamento de la empresa es obligatorio")
    private String departamento;

    @NotBlank(message = "La localidad de la empresa es obligatoria")
    private String localidad;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

}
