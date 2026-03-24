package com.agromvp.app_backend.service;

import com.agromvp.app_backend.dto.response.CatalogoResponse;
import com.agromvp.app_backend.dto.response.ProductoIndicadorResponse;
import com.agromvp.app_backend.dto.response.ProductoResponse;
import com.agromvp.app_backend.dto.response.TipoProductoResponse;
import com.agromvp.app_backend.entity.Producto;
import com.agromvp.app_backend.entity.ProductoIndicador;
import com.agromvp.app_backend.mapper.ProductoIndicadorMapper;
import com.agromvp.app_backend.mapper.ProductoMapper;
import com.agromvp.app_backend.mapper.TipoProductoMapper;
import com.agromvp.app_backend.repository.ProductoIndicadorRepository;
import com.agromvp.app_backend.repository.ProductoRepository;
import com.agromvp.app_backend.repository.TipoProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final TipoProductoRepository tipoProductoRepository;
    private final ProductoRepository productoRepository;
    private final ProductoIndicadorRepository productoIndicadorRepository;

    private final TipoProductoMapper tipoProductoMapper;
    private final ProductoMapper productoMapper;
    private final ProductoIndicadorMapper productoIndicadorMapper;

    public CatalogoResponse obtenerCatalogo(Long usuarioId) {

        List<TipoProductoResponse> tiposProducto = tipoProductoRepository
                .findByActivoTrueOrderByNombreAsc()
                .stream()
                .map(tipoProductoMapper::toResponse)
                .toList();

        List<Producto> productosEntity = usuarioId == null
                ? productoRepository.findByActivoTrueOrderByNombreAsc()
                : productoRepository.findActivosByUsuarioId(usuarioId);

        List<ProductoResponse> productos = productosEntity.stream()
                .map(productoMapper::toResponse)
                .toList();

        Set<Long> productosIdsCatalogo = productosEntity.stream()
                .map(Producto::getId)
                .collect(java.util.stream.Collectors.toSet());

        List<ProductoIndicador> relaciones = productoIndicadorRepository
                .findByActivoTrueAndVisibleTrueOrderByProductoIdAscOrdenVisualizacionAsc()
                .stream()
                .filter(relacion -> usuarioId == null || estaProductoDentroDeCatalogo(relacion, productosIdsCatalogo))
                .toList();

        List<ProductoIndicadorResponse> productoIndicadores = relaciones.stream()
                .map(productoIndicadorMapper::toResponse)
                .toList();

        return CatalogoResponse.builder()
                .tiposProducto(tiposProducto)
                .productos(productos)
                .productoIndicadores(productoIndicadores)
                .build();
    }

    private boolean estaProductoDentroDeCatalogo(ProductoIndicador relacion, Set<Long> productosIds) {
        Long productoIdRelacion = relacion.getProducto() != null ? relacion.getProducto().getId() : null;
        return productoIdRelacion != null && productosIds.contains(productoIdRelacion);
    }
}