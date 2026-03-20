package com.agromvp.app_backend.mapper;

import com.agromvp.app_backend.dto.response.ProductoResponse;
import com.agromvp.app_backend.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoResponse toResponse(Producto entity) {
        if (entity == null) {
            return null;
        }

        return ProductoResponse.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .imagenUrl(entity.getImagenUrl())
                .activo(entity.getActivo())
                .tipoProductoId(entity.getTipoProducto() != null ? entity.getTipoProducto().getId() : null)
                .tipoProductoCodigo(entity.getTipoProducto() != null ? entity.getTipoProducto().getCodigo() : null)
                .tipoProductoNombre(entity.getTipoProducto() != null ? entity.getTipoProducto().getNombre() : null)
                .build();
    }
}