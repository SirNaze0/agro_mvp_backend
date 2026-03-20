package com.agromvp.app_backend.repository;

import com.agromvp.app_backend.entity.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {

    List<TipoProducto> findByActivoTrueOrderByNombreAsc();

    Optional<TipoProducto> findByCodigo(String codigo);
}