package com.agromvp.app_backend.repository;

import com.agromvp.app_backend.entity.UsuarioProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioProductoRepository extends JpaRepository<UsuarioProducto, Long> {

    boolean existsByUsuarioIdAndProductoIdAndActivoTrue(Long usuarioId, Long productoId);
}
