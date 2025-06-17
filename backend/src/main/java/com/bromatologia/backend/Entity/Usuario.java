package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Enum.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    private String nombreUsuario;
    @NotNull(message = "el password no puede ser nulo")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "El usuario debe tener un rol asignado")
    private Rol rol;

    public Usuario(String nombreUsuario, String password, Rol rol){
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

}
