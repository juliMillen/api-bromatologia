package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.RegistroProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroProductoRepository extends JpaRepository<RegistroProducto, String> {
}
