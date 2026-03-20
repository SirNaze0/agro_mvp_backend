package com.agromvp.app_backend.controller;

import com.agromvp.app_backend.dto.response.TipoProductoResponse;
import com.agromvp.app_backend.service.TipoProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto")
@RequiredArgsConstructor
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    @GetMapping
    public List<TipoProductoResponse> listarActivos() {
        return tipoProductoService.listarActivos();
    }
}