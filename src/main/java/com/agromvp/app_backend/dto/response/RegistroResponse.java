package com.agromvp.app_backend.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
public class RegistroResponse {

    private Long id;
    private UUID clientUuid;
    private Long usuarioId;
    private Long productoId;
    private LocalDate fechaRegistro;
    private LocalTime horaRegistro;
    private Double latitud;
    private Double longitud;
    private String estadoRegistro;
    private String estadoFoto;
    private String observaciones;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}