package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRol;

    @NotBlank(message = "El rol es obligatorio")
    private String tipoRol;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    private List<Usuario> usuario;
}
