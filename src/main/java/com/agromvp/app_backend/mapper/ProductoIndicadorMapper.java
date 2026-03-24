package com.agromvp.app_backend.mapper;

import com.agromvp.app_backend.dto.response.ProductoIndicadorResponse;
import com.agromvp.app_backend.entity.ProductoIndicador;
import org.springframework.stereotype.Component;

@Component
public class ProductoIndicadorMapper {

    public ProductoIndicadorResponse toResponse(ProductoIndicador entity) {
        if (entity == null) {
            return null;
        }

        return ProductoIndicadorResponse.builder()
                .id(entity.getId())
                .obligatorio(entity.getObligatorio())
                .ordenVisualizacion(entity.getOrdenVisualizacion())
                .visible(entity.getVisible())
                .activo(entity.getActivo())
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoCodigo(entity.getProducto() != null ? entity.getProducto().getCodigo() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .indicadorId(entity.getIndicador() != null ? entity.getIndicador().getId() : null)
                .indicadorCodigo(entity.getIndicador() != null ? entity.getIndicador().getCodigo() : null)
                .indicadorNombre(entity.getIndicador() != null ? entity.getIndicador().getNombre() : null)
                .tipoIndicador(entity.getIndicador() != null ? entity.getIndicador().getTipoIndicador() : null)
                .unidadMedida(entity.getIndicador() != null ? entity.getIndicador().getUnidadMedida() : null)
                .tipoDato(entity.getIndicador() != null ? entity.getIndicador().getTipoDato() : null)
                .build();
    }
}