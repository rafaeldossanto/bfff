package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

public record AventuraResponse(
        String id,
        String usuarioId,
        String regiaoId,
        String destino,
        String status,
        String visibilidade,
        LocalDateTime criadoEm
) {}
