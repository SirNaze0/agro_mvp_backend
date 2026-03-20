package com.agromvp.app_backend.controller;

import com.agromvp.app_backend.dto.request.RegistroCreateRequest;
import com.agromvp.app_backend.dto.response.RegistroResponse;
import com.agromvp.app_backend.service.RegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService registroService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroResponse crear(@Valid @RequestBody RegistroCreateRequest request) {
        return registroService.crear(request);
    }
}