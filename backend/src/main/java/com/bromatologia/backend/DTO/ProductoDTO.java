package com.bromatologia.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private long id_Producto;
    private String marca;
    private String denominacion;
    private String nombreFantasia;
}
