package com.agromvp.app_backend.service;

import com.agromvp.app_backend.dto.response.CatalogoResponse;
import com.agromvp.app_backend.dto.response.ProductoIndicadorResponse;
import com.agromvp.app_backend.dto.response.ProductoResponse;
import com.agromvp.app_backend.dto.response.TipoProductoResponse;
import com.agromvp.app_backend.mapper.ProductoIndicadorMapper;
import com.agromvp.app_backend.mapper.ProductoMapper;
import com.agromvp.app_backend.mapper.TipoProductoMapper;
import com.agromvp.app_backend.repository.ProductoIndicadorRepository;
import com.agromvp.app_backend.repository.ProductoRepository;
import com.agromvp.app_backend.repository.TipoProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final TipoProductoRepository tipoProductoRepository;
    private final ProductoRepository productoRepository;
    private final ProductoIndicadorRepository productoIndicadorRepository;

    private final TipoProductoMapper tipoProductoMapper;
    private final ProductoMapper productoMapper;
    private final ProductoIndicadorMapper productoIndicadorMapper;

    public CatalogoResponse obtenerCatalogo() {

        List<TipoProductoResponse> tiposProducto = tipoProductoRepository
                .findByActivoTrueOrderByNombreAsc()
                .stream()
                .map(tipoProductoMapper::toResponse)
                .toList();

        List<ProductoResponse> productos = productoRepository
                .findByActivoTrueOrderByNombreAsc()
                .stream()
                .map(productoMapper::toResponse)
                .toList();

        List<ProductoIndicadorResponse> productoIndicadores = productoIndicadorRepository
                .findByActivoTrueAndVisibleTrueOrderByProductoIdAscOrdenVisualizacionAsc()
                .stream()
                .map(productoIndicadorMapper::toResponse)
                .toList();

        return CatalogoResponse.builder()
                .tiposProducto(tiposProducto)
                .productos(productos)
                .productoIndicadores(productoIndicadores)
                .build();
    }
}