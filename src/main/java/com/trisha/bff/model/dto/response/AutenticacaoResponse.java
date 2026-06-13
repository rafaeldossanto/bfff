package com.trisha.bff.model.dto.response;

/**
 * Resposta de login repassada do Cadastro: o usuario autenticado e o access
 * token da app que o front deve enviar no header Authorization.
 */
public record AutenticacaoResponse(
        UsuarioResponse usuario,
        String accessToken,
        long expiresInSegundos
) {}
