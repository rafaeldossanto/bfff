package com.trisha.bff.model.dto.request;

/**
 * Move uma aventura entre pastas. regiaoId nulo = tirar da pasta.
 */
public record MoverRegiaoRequest(
        String regiaoId
) {}
