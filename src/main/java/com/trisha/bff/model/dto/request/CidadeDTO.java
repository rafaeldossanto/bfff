package com.trisha.bff.model.dto.request;

/**
 * Cidade de uma regiao (request e response). Coordenadas opcionais.
 */
public record CidadeDTO(
        String nome,
        Double latitude,
        Double longitude,
        Double altitude
) {}
