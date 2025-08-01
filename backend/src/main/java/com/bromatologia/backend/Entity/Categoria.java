package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_establecimiento", nullable = true)
    private RegistroEstablecimiento registroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rubro_id")
    private Rubro rubro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

    @NotBlank
    private String nombreCategoria;


    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
