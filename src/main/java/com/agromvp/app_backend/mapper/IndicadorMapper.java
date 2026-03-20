package com.agromvp.app_backend.mapper;

import com.agromvp.app_backend.dto.response.IndicadorResponse;
import com.agromvp.app_backend.entity.Indicador;
import org.springframework.stereotype.Component;

@Component
public class IndicadorMapper {

    public IndicadorResponse toResponse(Indicador entity) {
        if (entity == null) {
            return null;
        }

        return IndicadorResponse.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .unidadMedida(entity.getUnidadMedida())
                .tipoDato(entity.getTipoDato())
                .formulaReferencia(entity.getFormulaReferencia())
                .activo(entity.getActivo())
                .build();
    }
}