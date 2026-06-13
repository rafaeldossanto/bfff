package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * O solicitante NAO vem no request — e derivado do token (propagado downstream).
 * O cliente informa apenas o alvo.
 */
public record AmizadeRequest(
        @NotBlank String receptorCodigo
) {}
