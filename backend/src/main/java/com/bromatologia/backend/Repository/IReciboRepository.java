package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReciboRepository extends JpaRepository<Recibo, Long> {
}
