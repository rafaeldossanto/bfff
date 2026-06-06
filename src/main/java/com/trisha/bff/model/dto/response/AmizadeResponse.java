package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

/**
 * Visao de amizade exposta pelo BFF ao front. Espelha os campos que o
 * front consome da resposta do servico APP. DTO proprio do BFF: desacopla
 * o contrato do front da estrutura interna do downstream.
 */
public record AmizadeResponse(
        String id,
        String solicitanteId,
        String receptorId,
        String status,
        LocalDateTime solicitadoEm,
        LocalDateTime respondidoEm
) {}
