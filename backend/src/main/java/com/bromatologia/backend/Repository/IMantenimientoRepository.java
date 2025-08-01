package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
}
