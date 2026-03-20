package com.agromvp.app_backend.service;

import com.agromvp.app_backend.dto.response.IndicadorResponse;
import com.agromvp.app_backend.entity.ProductoIndicador;
import com.agromvp.app_backend.mapper.IndicadorMapper;
import com.agromvp.app_backend.repository.ProductoIndicadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndicadorService {

    private final ProductoIndicadorRepository productoIndicadorRepository;
    private final IndicadorMapper indicadorMapper;

    public List<IndicadorResponse> listarPorProducto(Long productoId) {
        List<ProductoIndicador> relaciones =
                productoIndicadorRepository.findByProductoIdAndActivoTrueAndVisibleTrueOrderByOrdenVisualizacionAsc(productoId);

        return relaciones.stream()
                .map(ProductoIndicador::getIndicador)
                .map(indicadorMapper::toResponse)
                .toList();
    }
}