package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.EmpresaException;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Empresa {


    @Id
    @NotNull
    @Min(value = 20000000000L, message = "El CUIT debe tener al menos 11 dígitos")
    private long cuitEmpresa;

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

    public Empresa(long cuitEmpresa, String nombreEmpresa, Titular titular, String email, String telefono) {
        this.cuitEmpresa = cuitEmpresa;
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
