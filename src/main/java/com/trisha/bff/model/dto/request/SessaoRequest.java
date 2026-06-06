package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SessaoRequest(
        @NotBlank String caminhoId,
        @NotBlank String usuarioId
) {}
