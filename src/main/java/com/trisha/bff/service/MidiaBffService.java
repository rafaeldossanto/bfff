package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.MidiaRequest;
import com.trisha.bff.model.dto.response.MidiaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Cacheable(cacheNames = "midias-aventura", key = "#aventuraId")
    public List<MidiaResponse> getByAventura(String aventuraId) {
        return appClient.getMidiasByAventura(aventuraId);
    }

    @Cacheable(cacheNames = "midias-caminho", key = "#caminhoId")
    public List<MidiaResponse> getByCaminho(String caminhoId) {
        return appClient.getMidiasByCaminho(caminhoId);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "midias-aventura", allEntries = true),
            @CacheEvict(cacheNames = "midias-caminho", allEntries = true)
    })
    public void delete(String id) {
        log.info("BFF: deletando metadados de midia {}", id);
        appClient.deletarMidia(id);
    }
}
