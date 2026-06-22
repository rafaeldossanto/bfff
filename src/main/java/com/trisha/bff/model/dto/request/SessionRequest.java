package com.trisha.bff.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * autoFinish e finishDistanceMeters sao opcionais. Quando nulos, o
 * servico de Localizacao aplica os defaults (desligado, 5m). Para usar o
 * recurso, envie autoFinish=true e, se quiser, um raio diferente.
 */
public record SessionRequest(
        @JsonProperty("caminhoId") @NotBlank String pathId,
        @JsonProperty("usuarioId") @NotBlank String userId,
        @JsonProperty("terminoAutomatico") Boolean autoFinish,
        @JsonProperty("distanciaTerminoMetros") Double finishDistanceMeters
) {}
