package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

public record SessaoResponse(
        String id,
        String caminhoId,
        String usuarioId,
        String status,
        Boolean terminoAutomatico,
        Double distanciaTerminoMetros,
        Double distanciaTotalKm,
        LocalDateTime iniciadaEm,
        LocalDateTime finalizadaEm
) {}
