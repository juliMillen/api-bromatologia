package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.RegistroProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegistroProductoRepository extends JpaRepository<RegistroProducto, String> {

    @Query("SELECT rp from RegistroProducto rp LEFT JOIN FETCH rp.mantenimientos")
    List<RegistroProducto> findAllConProductoYMantenimientos();
}
