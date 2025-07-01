package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.RegistroEstablecimientoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroEstablecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRegistroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_titular", nullable = true)
    private Titular titular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = true)
    private Empresa empresa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    @OneToMany(mappedBy = "registroEstablecimiento", cascade = CascadeType.ALL)
    private List<Mantenimiento> mantenimientos = new ArrayList<>();


    @NotBlank(message = "la categoria anterior no puede estar vacia")
    private String categoriaAnt;

    @NotNull(message = "El arancel no puede estar vacio")
    private double arancel;

    @NotNull(message = "La fecha de emision es obligatoria")
    private LocalDate fechaEmision;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;

    public RegistroEstablecimiento(String categoriaAnt, double arancel, LocalDate fechaEmision, LocalDate fechaVencimiento, String estado) {
        this.categoriaAnt = categoriaAnt;
        this.arancel = arancel;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
    }

    public void asignarEmpresa(Empresa empresa) {
        if (empresa == null) {
            throw new RegistroEstablecimientoException("La empresa no puede ser null");
        }
        this.empresa = empresa;
    }

    public void asignarTitular(Titular titular) {
        if (titular == null) {
            throw new RegistroEstablecimientoException("El titular no puede estar vacio");
        }
        this.titular = titular;
    }

    public void asignarEstablecimiento(Establecimiento establecimiento) {
        if (establecimiento == null) {
            throw new RegistroEstablecimientoException("Establecimiento nulo");
        }
        this.establecimiento = establecimiento;
    }

    public void agregarMantenimiento(Mantenimiento mantenimiento) {
        if (mantenimiento != null) {
            mantenimientos.add(mantenimiento);
            mantenimiento.setRegistroEstablecimiento(this);
        }else{
            throw new RegistroEstablecimientoException("El mantenimiento es null");
        }
    }

}
