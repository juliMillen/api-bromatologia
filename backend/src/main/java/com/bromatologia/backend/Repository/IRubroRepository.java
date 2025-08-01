package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Rubro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRubroRepository extends JpaRepository<Rubro, Long> {
}
