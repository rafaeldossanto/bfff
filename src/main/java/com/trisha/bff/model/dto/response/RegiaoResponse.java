package com.trisha.bff.model.dto.response;

import com.trisha.bff.model.dto.request.CidadeDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Regiao = pasta do usuario. visibilidade como String (padrao do BFF — nao
 * duplica enums; o APP valida).
 */
public record RegiaoResponse(
        String id,
        String usuarioId,
        String nome,
        String descricao,
        String visibilidade,
        List<CidadeDTO> cidades,
        LocalDateTime criadoEm
) {}
