package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.RegistroProductoEstablecimiento;
import com.bromatologia.backend.Entity.RegistroProductoEstablecimientoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegistroProductoEstablecimientoRepository extends JpaRepository<RegistroProductoEstablecimiento, RegistroProductoEstablecimientoId> {

    @Query("SELECT rpe from RegistroProductoEstablecimiento rpe JOIN FETCH rpe.registroProducto rp LEFT JOIN FETCH rp.mantenimientos")
    List<RegistroProductoEstablecimiento> findAllConProductoYMantenimientos();
}
