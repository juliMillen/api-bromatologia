package com.bromatologia.backend.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El usuario es obligatorio")
    private String username;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    @Valid
    private RolDTO rol;
}
