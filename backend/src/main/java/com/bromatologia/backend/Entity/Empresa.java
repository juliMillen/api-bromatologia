package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.EmpresaException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Empresa {


    @Id
    private long cuit_Empresa;

    @NotBlank
    private String nombreEmpresa;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    private List<Establecimiento> establecimientos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "cuit_Titular")
    private Titular titular;

    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Formato de teléfono inválido")
    private String telefono;

    public Empresa(long cuit_Empresa, String nombreEmpresa, Titular titular, String email, String telefono) {
        this.cuit_Empresa = cuit_Empresa;
        this.nombreEmpresa = nombreEmpresa;
        this.titular = titular;
        this.email = email;
        this.telefono = telefono;
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        if(establecimiento != null) {
            establecimiento.setEmpresa(this);
            establecimientos.add(establecimiento);
        }else{
            throw new EmpresaException("El establecimiento es nulo");
        }
    }


}
