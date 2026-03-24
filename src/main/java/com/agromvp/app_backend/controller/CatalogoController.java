package com.agromvp.app_backend.controller;

import com.agromvp.app_backend.dto.response.CatalogoResponse;
import com.agromvp.app_backend.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping
    public CatalogoResponse obtenerCatalogo(@RequestParam(required = false) Long usuarioId) {
        return catalogoService.obtenerCatalogo(usuarioId);
    }
}
