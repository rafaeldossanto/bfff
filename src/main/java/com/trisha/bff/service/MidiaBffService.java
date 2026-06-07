package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.MidiaRequest;
import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Orquestra os metadados de midia sobre o servico APP. O BFF lida apenas com
 * metadados (incluindo a URL do binario): o upload do binario em si vai direto
 * do front para o servico de Midia, sem passar pelo BFF.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MidiaBffService {

    private final AppClient appClient;

    @Caching(evict = {
            @CacheEvict(cacheNames = "midias-aventura", allEntries = true),
            @CacheEvict(cacheNames = "midias-caminho", allEntries = true)
    })
    public MidiaResponse salvar(MidiaRequest request) {
        log.info("BFF: salvando metadados de midia na aventura {}", request.aventuraId());
        return appClient.salvarMidia(request);
    }

    @Cacheable(cacheNames = "midias-aventura",
            key = "#aventuraId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<MidiaResponse> getByAventura(String aventuraId, Pageable pageable) {
        return appClient.getMidiasByAventura(aventuraId, pageable);
    }

    @Cacheable(cacheNames = "midias-caminho",
            key = "#caminhoId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<MidiaResponse> getByCaminho(String caminhoId, Pageable pageable) {
        return appClient.getMidiasByCaminho(caminhoId, pageable);
    }

    @Caching(evict = {
   