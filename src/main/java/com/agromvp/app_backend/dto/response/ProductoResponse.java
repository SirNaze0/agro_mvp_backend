package com.agromvp.app_backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductoResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Boolean activo;

    private Long tipoProductoId;
    private String tipoProductoCodigo;
    private String tipoProductoNombre;
}