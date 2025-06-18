package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {


    @Id
    private long cuit_Empresa;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    private List<Establecimiento> establecimientos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuit_Titular")
    private Titular titular;

    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;

}
