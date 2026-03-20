package com.agromvp.app_backend.service;

import com.agromvp.app_backend.dto.response.ProductoResponse;
import com.agromvp.app_backend.mapper.ProductoMapper;
import com.agromvp.app_backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public List<ProductoResponse> listarActivos() {
        return productoRepository.findByActivoTrueOrderByNombreAsc()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    public List<ProductoResponse> listarPorTipo(Long tipoProductoId) {
        return productoRepository.findByTipoProductoIdAndActivoTrueOrderByNombreAsc(tipoProductoId)
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }
}