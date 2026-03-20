package com.agromvp.app_backend.mapper;
import com.agromvp.app_backend.dto.response.RegistroResponse;
import com.agromvp.app_backend.entity.Registro;
import org.springframework.stereotype.Component;

@Component
public class RegistroMapper {

    public RegistroResponse toResponse(Registro entity) {
        if (entity == null) {
            return null;
        }

        return RegistroResponse.builder()
                .id(entity.getId())
                .clientUuid(entity.getClientUuid())
                .usuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null)
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .fechaRegistro(entity.getFechaRegistro())
                .horaRegistro(entity.getHoraRegistro())
                .latitud(entity.getLatitud())
                .longitud(entity.getLongitud())
                .estadoRegistro(entity.getEstadoRegistro())
                .estadoFoto(entity.getEstadoFoto())
                .observaciones(entity.getObservaciones())
                .deleted(entity.getDeleted())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}