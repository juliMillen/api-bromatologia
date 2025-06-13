package com.bromatologia.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {


    @Id
    private long cuit_Empresa;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    private List<Establecimiento> establecimientos = new ArrayList<>();
    private String email;
    private long telefono;

}
