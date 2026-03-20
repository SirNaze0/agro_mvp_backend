package com.agromvp.app_backend.service;

import com.agromvp.app_backend.dto.response.TipoProductoResponse;
import com.agromvp.app_backend.mapper.TipoProductoMapper;
import com.agromvp.app_backend.repository.TipoProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;
    private final TipoProductoMapper tipoProductoMapper;

    public List<TipoProductoResponse> listarActivos() {
        return tipoProductoRepository.findByActivoTrueOrderByNombreAsc()
                .stream()
                .map(tipoProductoMapper::toResponse)
                .toList();
    }
}