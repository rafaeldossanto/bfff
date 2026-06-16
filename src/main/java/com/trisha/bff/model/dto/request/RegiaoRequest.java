package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Criacao/edicao de regiao (pasta). visibilidade como String (o APP valida o
 * enum). O dono vem do token, nao do corpo.
 */
public record RegiaoRequest(
        @NotBlank String nome,
        String descricao,
        String visibilidade,
        List<CidadeDTO> cidades
) {}
