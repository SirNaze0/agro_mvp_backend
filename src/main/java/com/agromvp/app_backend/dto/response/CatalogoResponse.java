package com.agromvp.app_backend.dto.response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CatalogoResponse {

    private List<TipoProductoResponse> tiposProducto;
    private List<ProductoResponse> productos;
    private List<ProductoIndicadorResponse> productoIndicadores;
}