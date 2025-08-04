package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.RegistroEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegistroEstablecimientoRepository extends JpaRepository<RegistroEstablecimiento, String> {

    @Query("SELECT re from RegistroEstablecimiento re LEFT JOIN FETCH re.mantenimientos")
    List<RegistroEstablecimiento> findAllConEstablecimientoYMantenimientos();
}
