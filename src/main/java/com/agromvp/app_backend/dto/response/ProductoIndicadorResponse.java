package com.agromvp.app_backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductoIndicadorResponse {

    private Long id;
    private Boolean obligatorio;
    private Integer ordenVisualizacion;
    private Boolean visible;
    private Boolean activo;

    private Long productoId;
    private String productoCodigo;
    private String productoNombre;

    private Long indicadorId;
    private String indicadorCodigo;
    private String indicadorNombre;
    private String unidadMedida;
    private String tipoDato;
}