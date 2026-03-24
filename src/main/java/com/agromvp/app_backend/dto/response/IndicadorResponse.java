package com.agromvp.app_backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndicadorResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private Integer tipoIndicador;
    private String descripcion;
    private String unidadMedida;
    private String tipoDato;
    private String formulaReferencia;
    private Boolean activo;
}