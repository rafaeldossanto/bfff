package com.trisha.bff.model.dto.response;

public record RegiaoResponse(
        String id,
        String nome,
        String descricao,
        Double latMin,
        Double latMax,
        Double lngMin,
        Double lngMax
) {}
