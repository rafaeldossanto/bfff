package com.trisha.bff.model.dto.response;

/**
 * Visao publica de usuario (busca para adicionar amigos). Espelha o DTO do APP:
 * so nome e codigoUsuario, sem email nem id interno.
 */
public record UsuarioPublicoResponse(
        String codigoUsuario,
        String nome
) {}
