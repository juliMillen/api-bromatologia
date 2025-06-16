package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @OneToOne(mappedBy = "empresa",fetch = FetchType.LAZY)
    private Titular titular;

    @NotBlank
    private String email;
    @NotBlank
    private long telefono;

}
