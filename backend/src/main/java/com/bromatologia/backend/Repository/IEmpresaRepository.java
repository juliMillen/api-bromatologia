package com.bromatologia.backend.Repository;

import com.bromatologia.backend.Entity.Empresa;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findEmpresaByRazonSocial(String razonSocial);


    Empresa findEmpresaByDepartamento(String departamento);

    Empresa findByCuitEmpresaAndRazonSocialAndDepartamento(long cuitEmpresa, String razonSocial, String departamento);
}
