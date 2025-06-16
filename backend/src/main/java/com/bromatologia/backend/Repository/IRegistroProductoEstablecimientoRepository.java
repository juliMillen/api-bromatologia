package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.RegistroProductoEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimientoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroProductoEstablecimientoRepository extends JpaRepository<RegistroProductoEstablecimiento, RegistroProductoEstablecimientoId> {
}
