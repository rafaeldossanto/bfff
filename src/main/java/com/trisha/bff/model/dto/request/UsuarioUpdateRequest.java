package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateRequest(
        @NotBlank String nome,
        @NotBlank @Email String email
) {}
