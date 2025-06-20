package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_registro_establecimiento")
    private RegistroEstablecimiento registroEstablecimiento;


    @NotBlank(message = "El tipo no puede estar vacio")
    private String tipo;

}
