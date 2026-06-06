package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CaminhoRequest(
        @NotBlank String aventuraId,
        @NotBlank String usuarioId,
        String cor,
        Integer numero
) {}
