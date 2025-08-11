package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.RegistroProductoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroProducto {

    @Id
    @Pattern(regexp = "08[-/]\\d{6}", message = "El RPPA debe tener el formato 08-000000 o 08/000000")
    private String rppa;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    @OneToOne(fetch = FetchType.LAZY)
    private RegistroEstablecimiento registroEstablecimiento;

    private String denominacion;

    private String marca;

    private String nombreFantasia;

    private String categoriaProducto;

    private long expediente;

    private String enlace;

    @NotBlank(message = "El elaborador no puede estar vacio")
    private String Elaborador;

    @OneToMany(mappedBy = "registroProducto",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mantenimiento> mantenimientos = new ArrayList<>();



    public RegistroProducto(String rppa, LocalDate fechaEmision, LocalDate fechaVencimiento, String denominacion, String marca, String nombreFantasia, String categoriaProducto, long expediente, String enlace) {
        this.rppa = rppa;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.denominacion = denominacion;
        this.marca = marca;
        this.nombreFantasia = nombreFantasia;
        this.categoriaProducto = categoriaProducto;
        this.expediente = expediente;
        this.enlace = enlace;
    }

    public void agregarRegistroEstablecimiento(RegistroEstablecimiento registroEstablecimiento){
        if(registroEstablecimiento == null){
            throw new RegistroProductoException("No hay un registro establecimiento");
        }
        this.registroEstablecimiento = registroEstablecimiento;
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
