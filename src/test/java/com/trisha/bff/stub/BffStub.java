package com.trisha.bff.stub;

import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;

import java.time.LocalDateTime;

/**
 * Facilitador de testes para os DTOs de resposta do BFF.
 */
public final class BffStub {

    public static final String USUARIO_ID = "usuario-1";
    public static final String AVENTURA_ID = "aventura-1";

    private BffStub() {
    }

    public static AmizadeResponse umaAmizade() {
        return new AmizadeResponse(
                "amizade-1", USUARIO_ID, "usuario-2", "ACEITA",
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static MidiaResponse umaMidia() {
        return new MidiaResponse(
                "midia-1", AVENTURA_ID, "caminho-1", "FOTO",
                "https://cdn/midia.jpg", 0.30, 1.5, LocalDateTime.now());
    }
}
