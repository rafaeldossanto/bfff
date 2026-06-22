package com.trisha.bff.service;

import com.trisha.bff.client.LocationClient;
import com.trisha.bff.model.dto.request.GpsPointRequest;
import com.trisha.bff.model.dto.request.SessionRequest;
import com.trisha.bff.model.dto.response.GpsPointResponse;
import com.trisha.bff.model.dto.response.SessionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestra sessoes de rastreamento e pontos GPS sobre o servico de Localizacao.
 *
 * Pontos GPS sao gravados em alta frequencia durante uma trilha em andamento,
 * entao registerPoint NAO cacheia (seria contraproducente) e invalida as
 * listas de pontos. As leituras de pontos so fazem sentido cachear apos a
 * sessao finalizar — para o MVP, o TTL curto ja cobre isso.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationBffService {

    private final LocationClient locationClient;

    public SessionResponse startSession(SessionRequest request) {
        log.info("BFF: iniciando sessao para caminho {}", request.pathId());
        return locationClient.startSession(request);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "pontos-sessao", allEntries = true),
            @CacheEvict(cacheNames = "pontos-caminho-gps", allEntries = true)
    })
    public GpsPointResponse registerPoint(GpsPointRequest request) {
        return locationClient.registerPoint(request);
    }

    public SessionResponse finishSession(String id) {
        log.info("BFF: finalizando sessao {}", id);
        return locationClient.finishSession(id);
    }

    public SessionResponse cancelSession(String id) {
        log.info("BFF: cancelando sessao {}", id);
        return locationClient.cancelSession(id);
    }

    @Cacheable(cacheNames = "sessao-caminho", key = "#pathId")
    public SessionResponse getSessionByPath(String pathId) {
        return locationClient.getSessionByPath(pathId);
    }

    @Cacheable(cacheNames = "pontos-sessao", key = "#sessionId")
    public List<GpsPointResponse> getPointsBySession(String sessionId) {
        return locationClient.getPointsBySession(sessionId);
    }

    @Cacheable(cacheNames = "pontos-caminho-gps", key = "#pathId")
    public List<GpsPointResponse> getPointsByPath(String pathId) {
        return locationClient.getPointsByPath(pathId);
    }
}
