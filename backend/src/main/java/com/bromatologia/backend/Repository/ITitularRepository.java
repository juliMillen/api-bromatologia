package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Titular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITitularRepository extends JpaRepository<Titular, Long> {
}
