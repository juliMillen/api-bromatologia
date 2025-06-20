package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    private String username;
    @NotNull(message = "el password no puede ser nulo")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El usuario debe tener un rol asignado")
    private Rol rol;

    public Usuario(String username, String password, Rol rol){
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

}
