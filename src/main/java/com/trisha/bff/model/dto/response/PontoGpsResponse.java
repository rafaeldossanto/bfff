package com.trisha.bff.model.dto.response;

import java.time.LocalDateTime;

public record PontoGpsResponse(
        String id,
        String sessaoId,
        Double latitude,
        Double longitude,
        Double altitude,
        Double precisao,
        Double velocidade,
        Integer ordem,
        LocalDateTime registradoEm,
        Boolean proximoDoInicio,
        Double distanciaDoInicioMetros
) {}
