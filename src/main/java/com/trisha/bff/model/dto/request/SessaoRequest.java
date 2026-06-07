package com.trisha.bff.model.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * terminoAutomatico e distanciaTerminoMetros sao opcionais. Quando nulos, o
 * servico de Localizacao aplica os defaults (desligado, 5m). Para usar o
 * recurso, envie terminoAutomatico=true e, se quiser, um raio diferente.
 */
public record SessaoRequest(
        @NotBlank String caminhoId,
        @NotBlank String usuarioId,
        Boolean terminoAutomatico,
        Double distanciaTerminoMetros
) {}
