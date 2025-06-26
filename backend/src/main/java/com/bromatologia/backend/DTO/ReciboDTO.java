package com.bromatologia.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReciboDTO {
    private long id_Recibo;
    private Date fecha_Recibo;
    private double importe;
}
