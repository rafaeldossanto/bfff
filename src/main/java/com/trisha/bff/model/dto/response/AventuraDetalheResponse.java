package com.trisha.bff.model.dto.response;

import java.util.List;

/**
 * Resposta agregada da "tela de aventura": junta a aventura, seus caminhos e
 * suas midias numa unica resposta. Evita que o app faca varias chamadas
 * sequenciais para montar a tela — uma unica chamada ao BFF traz tudo pronto.
 */
public record AventuraDetalheResponse(
        AventuraResponse aventura,
        List<CaminhoResponse> caminhos,
        List<MidiaResponse> midias
) {}
