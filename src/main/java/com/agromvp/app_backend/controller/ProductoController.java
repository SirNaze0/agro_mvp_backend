package com.agromvp.app_backend.controller;

import com.agromvp.app_backend.dto.response.ProductoResponse;
import com.agromvp.app_backend.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponse> listarActivos(@RequestParam(required = false) Long usuarioId) {
        return productoService.listarActivos(usuarioId);
    }

    @GetMapping("/por-tipo/{tipoProductoId}")
    public List<ProductoResponse> listarPorTipo(@PathVariable Long tipoProductoId,
                                                @RequestParam(required = false) Long usuarioId) {
        return productoService.listarPorTipo(tipoProductoId, usuarioId);
    }
}