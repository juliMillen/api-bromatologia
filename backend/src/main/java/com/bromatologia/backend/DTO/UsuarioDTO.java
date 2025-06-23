package com.bromatologia.backend.DTO;

import com.bromatologia.backend.Entity.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String username;
    private String role;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.role = usuario.getRol().name();
    }
}
