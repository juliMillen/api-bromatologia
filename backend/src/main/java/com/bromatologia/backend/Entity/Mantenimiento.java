package com.bromatologia.backend.Entity;

import jakarta.persistence.*;
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
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_mantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_establecimiento", nullable = false)
    private RegistroEstablecimiento registroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_producto", nullable = false)
    private RegistroProducto registroProducto;

    @OneToMany(mappedBy = "recibos",fetch = FetchType.LAZY)
    private List<Recibo> recibo = new ArrayList<>();

    private Date fecha_mantenimiento;
    private String tramiteAsociado;
    private String enlaceRecibido;

}
