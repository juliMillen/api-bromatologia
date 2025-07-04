package com.bromatologia.backend.DTO;

import com.bromatologia.backend.Entity.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull(message = "El rol no puede ser nulo")
    private RolDTO rol;
}
