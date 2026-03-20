package com.agromvp.app_backend.repository;

import com.agromvp.app_backend.entity.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndicadorRepository extends JpaRepository<Indicador, Long> {

    List<Indicador> findByActivoTrueOrderByNombreAsc();

    Optional<Indicador> findByCodigo(String codigo);
}