package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.RegistroEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroEstablecimientoRepository extends JpaRepository<RegistroEstablecimiento, Long> {
}
