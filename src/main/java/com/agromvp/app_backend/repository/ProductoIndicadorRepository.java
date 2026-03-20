package com.agromvp.app_backend.repository;

import com.agromvp.app_backend.entity.ProductoIndicador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoIndicadorRepository extends JpaRepository<ProductoIndicador, Long> {

    List<ProductoIndicador> findByProductoIdAndActivoTrueAndVisibleTrueOrderByOrdenVisualizacionAsc(Long productoId);

    List<ProductoIndicador> findByActivoTrueAndVisibleTrueOrderByProductoIdAscOrdenVisualizacionAsc();

    Optional<ProductoIndicador> findByProductoIdAndIndicadorIdAndActivoTrue(Long productoId, Long indicadorId);
}