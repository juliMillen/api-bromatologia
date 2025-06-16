package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepositoy extends JpaRepository<Usuario, Long> {
}
