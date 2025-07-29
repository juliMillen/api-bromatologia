package com.bromatologia.backend.Security;

import com.bromatologia.backend.DTO.*;
import com.bromatologia.backend.Entity.Rol;
import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Repository.IUsuarioRepository;
import com.bromatologia.backend.Service.RolService;
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

    @Autowired
    private RolService rolService;

    @PostMapping("/api/login")
    public ResponseEntity<?>login(@RequestBody LoginRequests loginRequests) {


        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequests.getUsername(), loginRequests.getPassword()
                    )
            );
            String token = jwtUtils.generateToken(authentication);
            return ResponseEntity.ok(new JwtResponse(token));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }


    @PostMapping("/api/register")
    public ResponseEntity<?>register(@RequestBody RegisterRequest registerRequest) {
        try{
            String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
            Rol rol = rolService.obtenerRolPorTipo("EMPLEADO");
            Usuario nuevoUsuario = new Usuario(registerRequest.getUsername(), hashedPassword, rol);
            usuarioRepository.save(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(nuevoUsuario));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol invalido");
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario");
        }
    }

    //metodo de mapeo DTO <---> entidad
    private UsuarioDTO convertirADTO(Usuario entidad){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entidad.getId());
        dto.setUsername(entidad.getUsername());
        dto.setRol(entidad.getRol().getTipoRol());

        /*Rol
        Rol rol = entidad.getRol();
        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(rol.getIdRol());
        rolDTO.setTipoRol(rol.getTipoRol());
        dto.setRol(rolDTO);*/
        return dto;
    }
}
