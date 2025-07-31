package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.RegistroEstablecimientoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroEstablecimiento {

    @Id
    private long rpe;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = true)
    private Empresa empresa;

    private String departamentoEst;

    private String localidadEst;

    private String direccionEst;

    private String expediente;

    private String enlace;

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL)
    private List<Categoria> listaCategorias = new ArrayList<>();


    @OneToMany(mappedBy = "registroEstablecimiento", cascade = CascadeType.ALL)
    private List<Mantenimiento> mantenimientos = new ArrayList<>();


    public RegistroEstablecimiento(long rpe, LocalDate fechaEmision, LocalDate fechaVencimiento, String departamentoEst, String localidadEst, String direccionEst, String expediente, String enlace) {
        this.rpe = rpe;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.departamentoEst = departamentoEst;
        this.localidadEst = localidadEst;
        this.direccionEst = direccionEst;
        this.expediente = expediente;
        this.enlace = enlace;
    }


    public void agregarMantenimiento(Mantenimiento mantenimiento) {
        if (mantenimiento != null) {
            mantenimientos.add(mantenimiento);
            mantenimiento.setRegistroEstablecimiento(this);
        }else{
            throw new RegistroEstablecimientoException("El mantenimiento es null");
        }
    }

    public void agregarCategoria(Categoria categoria) {
        if (categoria != null) {
            listaCategorias.add(categoria);
            categoria.setRegistroEstablecimiento(this);
        }else{
            throw new RegistroEstablecimientoException("La categoria es null");
        }
    }

}
