package com.bromatologia.backend.Security.DTO;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String role;   //ADMIN O EMPLEADO
}
