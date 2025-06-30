package com.bromatologia.backend.Entity;

import com.bromatologia.backend.Exception.EstablecimientoException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEstablecimiento;

    @OneToOne(mappedBy = "establecimiento", fetch = FetchType.LAZY)
    private RegistroEstablecimiento registroEstablecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    @OneToMany(mappedBy = "establecimiento",fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();

    @NotBlank(message = "El departamento no puede estar vacio")
    private String departamento;
    @NotBlank(message = "La localidad no puede estar vacia")
    private String localidad;
    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;

    public Establecimiento(String departamento, String localidad, String direccion) {
        this.departamento = departamento;
        this.localidad = localidad;
        this.direccion = direccion;
    }

    public void agregarProductos(Producto producto) {
        if(producto != null) {
            producto.setEstablecimiento(this); //bidireccionalidad
            productos.add(producto);
        }else{
            throw new EstablecimientoException("El producto es nulo");
        }
    }

}
