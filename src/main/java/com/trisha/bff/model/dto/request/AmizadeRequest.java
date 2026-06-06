package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AmizadeRequest(
        @NotBlank String solicitanteId,
        @NotBlank String receptorId
) {}
