package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.MantenimientoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_mantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_establecimiento", nullable = true)
    private RegistroEstablecimiento registroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_producto", nullable = true)
    private RegistroProducto registroProducto;

    @OneToMany(mappedBy = "mantenimiento", cascade = CascadeType.ALL)
    private List<Tramite> tramites = new ArrayList<>();

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private Date fecha_mantenimiento;
    @NotBlank(message = "El tramite asociado es obligatorio")
    private String tramiteAsociado;

    private String enlaceRecibido;

    public Mantenimiento(Date fecha_mantenimiento, String tramiteAsociado, String enlaceRecibido){
        this.fecha_mantenimiento = fecha_mantenimiento;
        this.tramiteAsociado = tramiteAsociado;
        this.enlaceRecibido = enlaceRecibido;
    }

    public void agregarTramite(Tramite tramite){
        if(tramite != null){
            tramite.setMantenimiento(this);
            this.tramites.add(tramite);
        }else{
            throw new MantenimientoException("El tramite es nulo");
        }
    }

}
