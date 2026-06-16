package com.trisha.bff.model.dto.response;

/**
 * Relacao de seguir entre o token e outro usuario (mutuo libera adicionar amigo).
 */
public record StatusSeguirResponse(
        boolean sigo,
        boolean meSegue,
        boolean mutuo
) {}
