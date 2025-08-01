package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idActividad;

    @OneToMany(mappedBy = "actividad")
    private List<Categoria> listaCategorias = new ArrayList<>();

    private String nombreActividad;

    public Actividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }
}
