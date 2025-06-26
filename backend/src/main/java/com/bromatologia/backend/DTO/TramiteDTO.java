package com.bromatologia.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TramiteDTO {

    private long id_Tramite;
    private String nombreTramite;

    //Recibo
    private ReciboDTO recibo;
}
