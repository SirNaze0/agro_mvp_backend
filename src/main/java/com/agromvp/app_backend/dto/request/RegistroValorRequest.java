package com.agromvp.app_backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class RegistroValorRequest {

    @NotNull(message = "clientUuid del valor es obligatorio")
    private UUID clientUuid;

    @NotNull(message = "indicadorId es obligatorio")
    private Long indicadorId;

    private Double valorNumerico;
    private String valorTexto;
    private Boolean valorBooleano;
    private LocalDate valorFecha;
}