package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

public record PontoInteresseResponse(
        String id,
        String caminhoId,
        String usuarioId,
        String tipo,
        String nome,
        String descricao,
        Double latitude,
        Double longitude,
        Integer nivelConfianca,
        LocalDateTime criadoEm
) {}
