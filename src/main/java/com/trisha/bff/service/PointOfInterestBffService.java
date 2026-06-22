package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.EvidenceRequest;
import com.trisha.bff.model.dto.request.PointOfInterestRequest;
import com.trisha.bff.model.dto.response.EvidenceResponse;
import com.trisha.bff.model.dto.response.PageResponse;
import com.trisha.bff.model.dto.response.PointOfInterestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Orquestra operacoes de ponto de interesse e evidencias sobre o servico APP.
 *
 * Adicionar evidencia muda o nivel de confianca do ponto (calculado pelo APP),
 * entao invalida tanto o ponto quanto a lista de pontos do caminho.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PointOfInterestBffService {

    private final AppClient appClient;

    @CacheEvict(cacheNames = "pontos-caminho", allEntries = true)
    public PointOfInterestResponse create(PointOfInterestRequest request) {
        log.info("BFF: criando ponto no caminho {}", request.pathId());
        return appClient.createPoint(request);
    }

    @Cacheable(cacheNames = "ponto", key = "#id")
    public PointOfInterestResponse getById(String id) {
        return appClient.getPoint(id);
    }

    @Cacheable(cacheNames = "pontos-caminho",
            key = "#pathId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PageResponse<PointOfInterestResponse> getByPath(String pathId, Pageable pageable) {
        return appClient.getPointsByPath(pathId, pageable);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "ponto", key = "#request.pointId()"),
            @CacheEvict(cacheNames = "pontos-caminho", allEntries = true)
    })
    public EvidenceResponse addEvidence(EvidenceRequest request) {
        log.info("BFF: adicionando evidencia ao ponto {}", request.pointId());
        return appClient.addEvidence(request);
    }
}
