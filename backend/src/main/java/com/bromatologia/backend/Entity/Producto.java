package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Producto;

    @OneToOne(mappedBy = "producto", fetch = FetchType.LAZY)
    private RegistroProducto registroProducto;

    private String marca;

    private String denominacion;

    private String nombreFantasia;
}
