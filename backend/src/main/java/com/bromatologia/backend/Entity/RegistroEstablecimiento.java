package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.RegistroEstablecimientoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroEstablecimiento {

    @Id
    @Pattern(regexp = "08[-/]\\d{6}", message = "El RPE debe tener el formato 08-000000 o 08/000000")
    private String rpe;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa",nullable = true)
    private Empresa empresa;

    private String departamentoEst;

    private String localidadEst;

    private String direccionEst;

    private long expediente;

    private String enlace;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( name= "registro_establecimiento_categoria", joinColumns = @JoinColumn(name = "rpe"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
    private Set<Categoria> listaCategorias = new HashSet<>();


    @OneToMany(mappedBy = "registroEstablecimiento", cascade = CascadeType.ALL)
    private List<Mantenimiento> mantenimientos = new ArrayList<>();


    public RegistroEstablecimiento(String rpe, LocalDate fechaEmision, LocalDate fechaVencimiento, String departamentoEst, String localidadEst, String direccionEst, long expediente, String enlace) {
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
            categoria.getRegistroEstablecimientos().add(this); //mantenemos sincronia
        }else{
            throw new RegistroEstablecimientoException("La categoria es null");
        }
    }

}
