package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

public record CaminhoResponse(
        String id,
        String aventuraId,
        String usuarioId,
        String cor,
        Integer numero,
        LocalDateTime iniciadoEm,
        LocalDateTime finalizadoEm,
        Double distanciaTotalKm
) {}
