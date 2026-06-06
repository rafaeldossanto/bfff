package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

/**
 * Visao de usuario exposta pelo BFF. O status (enum StatusCadastro no servico
 * Cadastro) e representado como String — o BFF nao duplica os enums dos servicos.
 */
public record UsuarioResponse(
        String id,
        String nome,
        String email,
        String codigoUsuario,
        String status,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {}
