package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Long> {
}
