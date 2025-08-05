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
public class Rubro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRubro;

    @OneToMany(mappedBy = "rubro", cascade = CascadeType.ALL)
    private List<Categoria> listaCategorias = new ArrayList<>();

    private String nombreRubro;


    public Rubro(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

    public void agregarCategoria(Categoria categoria){
        listaCategorias.add(categoria);
        categoria.setRubro(this);
    }
}
