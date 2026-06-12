package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EvidenciaRequest(
        @NotBlank String pontoId,
        @NotBlank String fotoUrl,
        @NotBlank String tipoEvidencia,
        @NotNull Double latCaptura,
        @NotNull Double lngCaptura
) {}
