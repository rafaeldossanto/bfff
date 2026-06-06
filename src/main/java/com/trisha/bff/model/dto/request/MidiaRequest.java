package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Metadados de midia repassados ao APP. O 'caminhoId' e opcional (midia avulsa
 * na aventura); o 'url' ja aponta para o binario no storage (MinIO/R2),
 * subido diretamente pelo front no servico de Midia.
 */
public record MidiaRequest(
        @NotBlank String aventuraId,
        String caminhoId,
        @NotBlank String usuarioId,
        @NotBlank String tipo,
        @NotBlank String url,
        Double latCaptura,
        Double lngCaptura,
        Double distanciaNaCapturaKm,
        Double percentualNoCaminho
) {}
