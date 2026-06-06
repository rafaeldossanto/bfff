package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

/**
 * Visao de midia exposta pelo BFF ao front. Espelha os metadados de midia
 * (incluindo a URL do binario no storage) que o APP ja mantem — por isso o
 * BFF resolve "midias de uma aventura" batendo apenas no APP.
 */
public record MidiaResponse(
        String id,
        String aventuraId,
        String caminhoId,
        String tipo,
        String url,
        Double percentualNoCaminho,
        Double distanciaNaCapturaKm,
        LocalDateTime capturadaEm
) {}
