package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActividadRepository extends JpaRepository<Actividad, Long> {
}
