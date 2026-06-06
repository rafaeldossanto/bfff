package com.trisha.bff.service;

import com.trisha.bff.client.LocalizacaoClient;
import com.trisha.bff.model.dto.request.PontoGpsRequest;
import com.trisha.bff.model.dto.request.SessaoRequest;
import com.trisha.bff.model.dto.response.PontoGpsResponse;
import com.trisha.bff.model.dto.response.SessaoResponse;
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
 * entao registrarPonto NAO cacheia (seria contraproducente) e invalida as
 * listas de pontos. As leituras de pontos so fazem sentido cachear apos a
 * sessao finalizar — para o MVP, o TTL curto ja cobre isso.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocalizacaoBffService {

    private final LocalizacaoClient localizacaoClient;

    public SessaoResponse iniciarSessao(SessaoRequest request) {
        log.info("BFF: iniciando sessao para caminho {}", request.caminhoId());
        return localizacaoClient.iniciarSessao(request);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "pontos-sessao", allEntries = true),
            @CacheEvict(cacheNames = "pontos-caminho-gps", allEntries = true)
    })
    public PontoGpsResponse registrarPonto(PontoGpsRequest request) {
        return localizacaoClient.registrarPonto(request);
    }

    public SessaoResponse finalizarSessao(String id) {
        log.info("BFF: finalizando sessao {}", id);
        return localizacaoClient.finalizarSessao(id);
    }

    public SessaoResponse cancelarSessao(String id) {
        log.info("BFF: cancelando sessao {}", id);
        return localizacaoClient.cancelarSessao(id);
    }

    @Cacheable(cacheNames = "sessao-caminho", key = "#caminhoId")
    public SessaoResponse getSessaoByCaminho(String caminhoId) {
        return localizacaoClient.getSessaoByCaminho(caminhoId);
    }

    @Cacheable(cacheNames = "pontos-sessao", key = "#sessaoId")
    public List<PontoGpsResponse> getPontosBySessao(String sessaoId) {
        return localizacaoClient.getPontosBySessao(sessaoId);
    }

    @Cacheable(cacheNames = "pontos-caminho-gps", key = "#caminhoId")
    public List<PontoGpsResponse> getPontosByCaminho(String caminhoId) {
        return localizacaoClient.getPontosByCaminho(caminhoId);
    }
}
