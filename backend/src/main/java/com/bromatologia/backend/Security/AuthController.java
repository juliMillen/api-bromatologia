package com.bromatologia.backend.Security;

import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Enums.Rol;
import com.bromatologia.backend.Repository.IUsuarioRepository;
import com.bromatologia.backend.Security.DTO.JwtResponse;
import com.bromatologia.backend.Security.DTO.LoginRequests;
import com.bromatologia.backend.Security.DTO.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequests loginRequests) {

        System.out.println("=== DEBUG LOGIN ===");
        System.out.println("Username recibido: " + loginRequests.getUsername());
        System.out.println("Password recibido: " + loginRequests.getPassword());
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequests.getUsername(), loginRequests.getPassword()
                    )
            );
            System.out.println("Autenticacion Exitosa");
            String token = jwtUtils.generateToken(authentication);
            return ResponseEntity.ok(new JwtResponse(token));
        }catch(Exception e){
            System.out.println("Error en autenticación: " + e.getClass().getSimpleName());
            System.out.println("Mensaje de error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }


    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterRequest registerRequest) {
        try{
            String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
            Rol rolEnum = Rol.valueOf(registerRequest.getRole().toUpperCase()); //convierte todo a mayuscula sin importar que se lo pase en minuscula
            Usuario nuevoUsuario = new Usuario(registerRequest.getUsername(), hashedPassword, rolEnum);
            usuarioRepository.save(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol invalido");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario");
        }
    }
}
