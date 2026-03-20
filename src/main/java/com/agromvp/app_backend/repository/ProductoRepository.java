package com.agromvp.app_backend.repository;

import com.agromvp.app_backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivoTrueOrderByNombreAsc();

    List<Producto> findByTipoProductoIdAndActivoTrueOrderByNombreAsc(Long tipoProductoId);

    Optional<Producto> findByCodigo(String codigo);
}