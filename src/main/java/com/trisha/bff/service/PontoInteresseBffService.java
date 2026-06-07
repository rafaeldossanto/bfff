package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.EvidenciaRequest;
import com.trisha.bff.model.dto.request.PontoInteresseRequest;
import com.trisha.bff.model.dto.response.EvidenciaResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.PontoInteresseResponse;
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
public class PontoInteresseBffService {

    private final AppClient appClient;

    @CacheEvict(cacheNames = "pontos-caminho", allEntries = true)
    public PontoInteresseResponse create(PontoInteresseRequest request) {
        log.info("BFF: criando ponto no caminho {}", request.caminhoId());
        return appClient.criarPonto(request);
    }

    @Cacheable(cacheNames = "ponto", key = "#id")
    public PontoInteresseResponse getById(String id) {
        return appClient.getPonto(id);
    }

    @Cacheable(cacheNames = "pontos-caminho",
            key = "#caminhoId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<PontoInteresseResponse> getByCaminho(String caminhoId, Pageable pageable) {
        return appClient.getPontosByCaminho(caminhoId, pageable);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "ponto", key = "#request.pontoId()"),
            @CacheEvict(cacheNames = "pontos-caminho", allEntries = true)
    })
    public EvidenciaResponse adicionarEvidencia(EvidenciaRequest request) {
        log.info("BFF: adicionando evidencia ao ponto {}", request.pontoId());
        return appClient.adicionarEvidencia(request);
    }
}
