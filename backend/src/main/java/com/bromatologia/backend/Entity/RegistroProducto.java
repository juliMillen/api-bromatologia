package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_RegistroProducto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToMany(mappedBy = "registroProducto",fetch = FetchType.LAZY)
    private List<Mantenimiento> mantenimiento = new ArrayList<>();


    @OneToMany(mappedBy = "registroProducto",fetch = FetchType.LAZY)
    private List<RegistroEstablecimiento> registrosPorEstablecimientos = new ArrayList<>();


    @NotBlank(message = "El tipo no puede estar vacio")
    private String tipo;

}
