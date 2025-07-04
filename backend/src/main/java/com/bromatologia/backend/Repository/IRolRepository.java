package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByTipoRol(String tipoRol);
}
