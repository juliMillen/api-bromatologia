package com.bromatologia.backend.DTO;

import com.bromatologia.backend.Entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    @NotBlank(message = "El nombre de usario es obligatorio")
    private String username;
    @NotBlank(message = "El rol del usuario es obligatorio")
    private String role;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.role = usuario.getRol().name();
    }
}
