package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request de criacao de usuario, repassado ao servico Cadastro. As validacoes
 * (@NotBlank/@Email) barram payloads invalidos ja no BFF, antes do hop de rede.
 */
public record UsuarioCreateRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha
) {}
