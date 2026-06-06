package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

public record EvidenciaResponse(
        String id,
        String pontoId,
        String usuarioId,
        String fotoUrl,
        String tipoEvidencia,
        Boolean validada,
        LocalDateTime criadoEm
) {}
