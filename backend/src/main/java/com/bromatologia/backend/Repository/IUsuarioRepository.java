package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Usuario;
import com.bromatologia.backend.Enum.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> obtenerUsuarioPorNombre(String username);
}
