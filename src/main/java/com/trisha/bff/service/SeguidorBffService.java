package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.ContadoresResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.StatusSeguirResponse;
import com.trisha.bff.model.dto.response.UsuarioPublicoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** Orquestra seguir/deixar de seguir, listas, contadores e status sobre o APP. */
@Service
@RequiredArgsConstructor
@Slf4j
public class SeguidorBffService {

    private final AppClient appClient;

    public void seguir(String seguidoId) {
        log.info("BFF: seguir {}", seguidoId);
        appClient.seguir(seguidoId);
    }

    public void deixarDeSeguir(String seguidoId) {
        log.info("BFF: deixar de seguir {}", seguidoId);
        appClient.deixarDeSeguir(seguidoId);
    }

    public PaginaResponse<UsuarioPublicoResponse> seguidores(String usuarioId, Pageable pageable) {
        return appClient.getSeguidores(usuarioId, pageable);
    }

    public PaginaResponse<UsuarioPublicoResponse> seguindo(String usuarioId, Pageable pageable) {
        return appClient.getSeguindo(usuarioId, pageable);
    }

    public ContadoresResponse contadores(String usuarioId) {
        return appClient.getContadores(usuarioId);
    }

    public StatusSeguirResponse status(String usuarioId) {
        return appClient.getStatusSeguir(usuarioId);
    }
}
