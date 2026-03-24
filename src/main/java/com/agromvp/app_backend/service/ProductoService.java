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
        return listarActivos(null);
    }

    public List<ProductoResponse> listarActivos(Long usuarioId) {
        List<ProductoResponse> productos = (usuarioId == null
                ? productoRepository.findByActivoTrueOrderByNombreAsc()
                : productoRepository.findActivosByUsuarioId(usuarioId))
                .stream()
                .map(productoMapper::toResponse)
                .toList();

        return productos;
    }

    public List<ProductoResponse> listarPorTipo(Long tipoProductoId) {
        return listarPorTipo(tipoProductoId, null);
    }

    public List<ProductoResponse> listarPorTipo(Long tipoProductoId, Long usuarioId) {
        List<ProductoResponse> productos = (usuarioId == null
                ? productoRepository.findByTipoProductoIdAndActivoTrueOrderByNombreAsc(tipoProductoId)
                : productoRepository.findByTipoProductoIdAndUsuarioIdActivos(tipoProductoId, usuarioId))
                .stream()
                .map(productoMapper::toResponse)
                .toList();

        return productos;
    }
}