package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * A visibilidade e enviada como String (ex.: "PRIVADA", "PUBLICA") e validada
 * no servico APP, que conhece o enum VisibilidadeAventura. Manter String aqui
 * desacopla o BFF de duplicar os enums dos servicos.
 */
public record AventuraRequest(
        @NotBlank String regiaoId,
        @NotBlank String destino,
        String visibilidade
) {}
