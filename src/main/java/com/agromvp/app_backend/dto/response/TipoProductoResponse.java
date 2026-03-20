package com.agromvp.app_backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TipoProductoResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}