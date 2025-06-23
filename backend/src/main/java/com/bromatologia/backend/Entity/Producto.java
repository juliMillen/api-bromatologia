package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Producto;

    @OneToOne(mappedBy = "producto", fetch = FetchType.LAZY)
    private RegistroProducto registroProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_producto",nullable = false)
    private Establecimiento establecimiento;

    @NotBlank(message = "La marca no puede estar vacia")
    private String marca;

    @NotBlank(message = "la denominacion no puede estar vacia")
    private String denominacion;

    @NotBlank(message = "El nombre de fantasia de no puede estar vacio")
    private String nombreFantasia;

    public Producto( String marca, String denominacion, String nombreFantasia) {
        this.marca = marca;
        this.denominacion = denominacion;
        this.nombreFantasia = nombreFantasia;
    }
}
