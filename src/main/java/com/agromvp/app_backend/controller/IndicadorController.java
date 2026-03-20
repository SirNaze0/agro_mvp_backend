package com.agromvp.app_backend.controller;

import com.agromvp.app_backend.dto.response.IndicadorResponse;
import com.agromvp.app_backend.service.IndicadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/indicadores")
@RequiredArgsConstructor
public class IndicadorController {

    private final IndicadorService indicadorService;

    @GetMapping("/por-producto/{productoId}")
    public List<IndicadorResponse> listarPorProducto(@PathVariable Long productoId) {
        return indicadorService.listarPorProducto(productoId);
    }
}