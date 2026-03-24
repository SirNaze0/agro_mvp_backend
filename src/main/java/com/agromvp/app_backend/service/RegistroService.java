package com.agromvp.app_backend.service;

import com.agromvp.app_backend.config.BadRequestException;
import com.agromvp.app_backend.config.ResourceNotFoundException;
import com.agromvp.app_backend.dto.request.RegistroCreateRequest;
import com.agromvp.app_backend.dto.request.RegistroValorRequest;
import com.agromvp.app_backend.dto.response.RegistroResponse;
import com.agromvp.app_backend.entity.*;
import com.agromvp.app_backend.mapper.RegistroMapper;
import com.agromvp.app_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final RegistroValorRepository registroValorRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final IndicadorRepository indicadorRepository;
    private final ProductoIndicadorRepository productoIndicadorRepository;
    private final UsuarioProductoRepository usuarioProductoRepository;
    private final RegistroMapper registroMapper;

    @Transactional
    public RegistroResponse crear(RegistroCreateRequest request) {

        UUID registroClientUuid = request.getClientUuid() != null ? request.getClientUuid() : UUID.randomUUID();
        if (registroRepository.findByClientUuid(registroClientUuid).isPresent()) {
            throw new BadRequestException("Ya existe un registro con ese clientUuid.");
        }

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getUsuarioId()));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + request.getProductoId()));

        validarAsignacionUsuarioProducto(usuario.getId(), producto.getId());
        validarEstados(request);
        validarUbicacion(request);

        LocalDateTime ahora = LocalDateTime.now();

        Registro registro = Registro.builder()
                .clientUuid(registroClientUuid)
                .usuario(usuario)
                .producto(producto)
                .fechaRegistro(request.getFechaRegistro())
                .horaRegistro(request.getHoraRegistro())
                .latitud(request.getLatitud())
                .longitud(request.getLongitud())
                .estadoRegistro(request.getEstadoRegistro())
                .estadoFoto(request.getEstadoFoto())
                .observaciones(request.getObservaciones())
                .deleted(false)
                .createdAt(ahora)
                .updatedAt(ahora)
                .build();

        Registro registroGuardado = registroRepository.save(registro);

        for (RegistroValorRequest valorRequest : request.getValores()) {
            UUID valorClientUuid = valorRequest.getClientUuid() != null ? valorRequest.getClientUuid() : UUID.randomUUID();

            if (registroValorRepository.findByClientUuid(valorClientUuid).isPresent()) {
                throw new BadRequestException(
                        "Ya existe un valor de registro con clientUuid: " + valorClientUuid
                );
            }

            Indicador indicador = indicadorRepository.findById(valorRequest.getIndicadorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Indicador no encontrado con id: " + valorRequest.getIndicadorId()
                    ));

            boolean indicadorValidoParaProducto = productoIndicadorRepository
                    .findByProductoIdAndIndicadorIdAndActivoTrue(producto.getId(), indicador.getId())
                    .isPresent();

            if (!indicadorValidoParaProducto) {
                throw new BadRequestException(
                        "El indicador " + indicador.getCodigo() +
                                " no está asociado al producto " + producto.getCodigo()
                );
            }

            validarValorSegunTipo(indicador, valorRequest);

            RegistroValor registroValor = RegistroValor.builder()
                    .clientUuid(valorClientUuid)
                    .registro(registroGuardado)
                    .indicador(indicador)
                    .valorNumerico(valorRequest.getValorNumerico())
                    .valorTexto(valorRequest.getValorTexto())
                    .valorBooleano(valorRequest.getValorBooleano())
                    .valorFecha(valorRequest.getValorFecha())
                    .createdAt(ahora)
                    .updatedAt(ahora)
                    .build();

            registroValorRepository.save(registroValor);
        }

        return registroMapper.toResponse(registroGuardado);
    }

    private void validarEstados(RegistroCreateRequest request) {
        if (request.getEstadoRegistro() == null || request.getEstadoRegistro().isBlank()) {
            request.setEstadoRegistro("PENDIENTE");
        }

        if (!request.getEstadoRegistro().matches("PENDIENTE|COMPLETO|SINCRONIZADO|ERROR")) {
            throw new BadRequestException("estadoRegistro debe ser PENDIENTE, COMPLETO, SINCRONIZADO o ERROR");
        }

        if (request.getEstadoFoto() == null || request.getEstadoFoto().isBlank()) {
            request.setEstadoFoto("SIN_FOTO");
        }

        if (!request.getEstadoFoto().matches("SIN_FOTO|PENDIENTE|SINCRONIZADA|ERROR")) {
            throw new BadRequestException("estadoFoto debe ser SIN_FOTO, PENDIENTE, SINCRONIZADA o ERROR");
        }
    }

    private void validarAsignacionUsuarioProducto(Long usuarioId, Long productoId) {
        boolean asignacionActiva = usuarioProductoRepository
                .existsByUsuarioIdAndProductoIdAndActivoTrue(usuarioId, productoId);

        if (!asignacionActiva) {
            throw new BadRequestException("El producto no está asignado al usuario o la asignación está inactiva");
        }
    }

    private void validarUbicacion(RegistroCreateRequest request) {
        if (request.getLatitud() != null && (request.getLatitud() < -90 || request.getLatitud() > 90)) {
            throw new BadRequestException("La latitud debe estar entre -90 y 90");
        }

        if (request.getLongitud() != null && (request.getLongitud() < -180 || request.getLongitud() > 180)) {
            throw new BadRequestException("La longitud debe estar entre -180 y 180");
        }
    }

    private void validarValorSegunTipo(Indicador indicador, RegistroValorRequest request) {
        String tipoDato = indicador.getTipoDato();

        switch (tipoDato) {
            case "NUMERICO":
                if (request.getValorNumerico() == null) {
                    throw new BadRequestException("El indicador " + indicador.getCodigo() + " requiere valorNumerico");
                }
                break;

            case "TEXTO":
                if (request.getValorTexto() == null || request.getValorTexto().isBlank()) {
                    throw new BadRequestException("El indicador " + indicador.getCodigo() + " requiere valorTexto");
                }
                break;

            case "BOOLEANO":
                if (request.getValorBooleano() == null) {
                    throw new BadRequestException("El indicador " + indicador.getCodigo() + " requiere valorBooleano");
                }
                break;

            case "FECHA":
                if (request.getValorFecha() == null) {
                    throw new BadRequestException("El indicador " + indicador.getCodigo() + " requiere valorFecha");
                }
                break;

            default:
                throw new BadRequestException("Tipo de dato no soportado para indicador: " + tipoDato);
        }
    }
}