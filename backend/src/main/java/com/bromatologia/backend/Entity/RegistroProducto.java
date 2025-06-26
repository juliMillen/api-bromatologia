package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.RegistroProductoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_RegistroProducto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    @OneToMany(mappedBy = "registroProducto",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mantenimiento> mantenimientos = new ArrayList<>();


    @NotBlank(message = "El tipo no puede estar vacio")
    private String tipo;


    public RegistroProducto(String tipo) {
        this.tipo = tipo;
    }

    public void asignarProducto(Producto producto) {
        if (producto == null) {
            throw new RegistroProductoException("El producto no puede ser nulo");
        }
        this.producto = producto;
    }


    public void agregarMantenimiento(Mantenimiento mantenimiento){
        if(mantenimiento != null){
            mantenimientos.add(mantenimiento);
            mantenimiento.setRegistroProducto(this);
        }else{
            throw new RegistroProductoException("El mantenimiento es nulo");
        }
    }

}
