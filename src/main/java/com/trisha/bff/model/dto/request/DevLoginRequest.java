package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Atalho de login de desenvolvimento repassado ao Cadastro (profile dev): emite
 * o token da app a partir de email/nome, sem provedor social.
 */
public record DevLoginRequest(
        @NotBlank @Email String email,
        @NotBlank String nome
) {}
