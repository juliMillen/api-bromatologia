package com.bromatologia.backend.DTO;

import com.bromatologia.backend.Entity.Tramite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MantenimientoDTO {

    private Date fechaMantenimiento;
    private String enlaceRecibido;

    //Datos del tramite
    private List<Tramite> tramites;
}
