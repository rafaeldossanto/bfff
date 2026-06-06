package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.AmizadeRequest;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestra operacoes de amizade sobre o servico APP.
 *
 * Solicitar e responder mudam as listas de pendentes/amigos dos dois usuarios
 * envolvidos. Como a chave do cache e por usuario e nao temos os dois ids em
 * toda operacao, invalidamos as listas inteiras (allEntries) — simples e
 * seguro para um dominio de baixo volume de escrita.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AmizadeBffService {

    private final AppClient appClient;

    @Caching(evict = {
            @CacheEvict(cacheNames = "amigos", allEntries = true),
            @CacheEvict(cacheNames = "amizades-pendentes", allEntries = true)
    })
    public AmizadeResponse solicitar(AmizadeRequest request) {
        log.info("BFF: solicitacao de amizade de {} para {}", request.solicitanteId(), request.receptorId());
        return appClient.solicitarAmizade(request);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "amigos", allEntries = true),
            @CacheEvict(cacheNames = "amizades-pendentes", allEntries = true)
    })
    public AmizadeResponse responder(String id, String status) {
        log.info("BFF: respondendo amizade {} com status {}", id, status);
        return appClient.responderAmizade(id, status);
    }

    @Cacheable(cacheNames = "amizades-pendentes", key = "#usuarioId")
    public List<AmizadeResponse> getPendentes(String usuarioId) {
        return appClient.getPendentes(usuarioId);
    }

    @Cacheable(cacheNames = "amigos", key = "#usuarioId")
    public List<AmizadeResponse> getAmigos(String usuarioId) {
        return appClient.getAmigos(usuarioId);
    }
}
