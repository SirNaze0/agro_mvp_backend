package com.agromvp.app_backend.mapper;

import com.agromvp.app_backend.dto.response.TipoProductoResponse;
import com.agromvp.app_backend.entity.TipoProducto;
import org.springframework.stereotype.Component;

@Component
public class TipoProductoMapper {

    public TipoProductoResponse toResponse(TipoProducto entity) {
        if (entity == null) {
            return null;
        }

        return TipoProductoResponse.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .activo(entity.getActivo())
                .build();
    }
}