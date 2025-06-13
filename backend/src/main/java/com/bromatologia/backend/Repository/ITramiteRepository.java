package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Tramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITramiteRepository extends JpaRepository<Tramite, Long> {
}
