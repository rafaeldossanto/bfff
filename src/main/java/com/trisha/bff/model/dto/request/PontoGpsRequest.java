package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PontoGpsRequest(
        @NotBlank String sessaoId,
        @NotNull Double latitude,
        @NotNull Double longitude,
        Double altitude,
        Double precisao,
        Double velocidade
) {}
