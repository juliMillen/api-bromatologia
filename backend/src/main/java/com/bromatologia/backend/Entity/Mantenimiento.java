package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.MantenimientoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_establecimiento", referencedColumnName = "rpe", nullable = true)
    private RegistroEstablecimiento registroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_producto", referencedColumnName = "rppa", nullable = true)
    private RegistroProducto registroProducto;

    @OneToMany(mappedBy = "mantenimiento", cascade = CascadeType.ALL)
    private List<Tramite> tramites = new ArrayList<>();

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private LocalDate fechaMantenimiento;

    private String enlaceRecibido;

    public Mantenimiento(LocalDate fechaMantenimiento, String enlaceRecibido){
        this.fechaMantenimiento = fechaMantenimiento;
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
