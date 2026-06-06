package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PontoInteresseRequest(
        @NotBlank String caminhoId,
        @NotBlank String usuarioId,
        @NotBlank String tipo,
        String nome,
        String descricao,
        @NotNull Double latitude,
        @NotNull Double longitude
) {}
