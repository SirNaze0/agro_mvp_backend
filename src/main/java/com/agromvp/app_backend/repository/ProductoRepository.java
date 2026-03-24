package com.agromvp.app_backend.repository;

import com.agromvp.app_backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivoTrueOrderByNombreAsc();

    List<Producto> findByTipoProductoIdAndActivoTrueOrderByNombreAsc(Long tipoProductoId);

    @Query("SELECT p FROM Producto p " +
            "JOIN UsuarioProducto up ON up.producto.id = p.id " +
            "WHERE p.activo = true " +
            "AND up.activo = true " +
            "AND up.usuario.id = :usuarioId " +
            "ORDER BY p.nombre ASC")
    List<Producto> findActivosByUsuarioId(Long usuarioId);

    @Query("SELECT p FROM Producto p " +
            "JOIN UsuarioProducto up ON up.producto.id = p.id " +
            "WHERE p.activo = true " +
            "AND up.activo = true " +
            "AND up.usuario.id = :usuarioId " +
            "AND p.tipoProducto.id = :tipoProductoId " +
            "ORDER BY p.nombre ASC")
    List<Producto> findByTipoProductoIdAndUsuarioIdActivos(Long tipoProductoId, Long usuarioId);

    Optional<Producto> findByCodigo(String codigo);
}