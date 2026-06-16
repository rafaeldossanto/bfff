package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.AventuraRequest;
import com.trisha.bff.model.dto.request.MoverRegiaoRequest;
import com.trisha.bff.model.dto.response.AventuraDetalheResponse;
import com.trisha.bff.model.dto.response.AventuraResponse;
import com.trisha.bff.model.dto.response.CaminhoResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestra operacoes de aventura sobre o servico APP.
 *
 * O cache "aventura" guarda a aventura por id; o cache "aventuras-usuario"
 * guarda a lista por usuario. Escritas (status, participante, delete)
 * invalidam ambos, pois alteram tanto a aventura quanto as listas que a
 * contem — invalidar a lista inteira do usuario e mais simples e seguro do
 * que tentar atualizar entradas individuais.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AventuraBffService {

    /**
     * Pagina usada na agregacao da tela de aventura. A tela mostra os primeiros
     * caminhos e midias da aventura; o front pagina o resto via os endpoints
     * dedicados de caminho/midia. Mantemos um teto generoso para nao perder itens
     * na visao inicial sem trazer colecoes ilimitadas.
     */
    private static final Pageable PRIMEIRA_PAGINA_DETALHE = PageRequest.of(0, 50);

    private final AppClient appClient;

    public AventuraResponse create(AventuraRequest request) {
        log.info("BFF: criando aventura para o destino {}", request.destino());
        return appClient.criarAventura(request);
    }

    @Cacheable(cacheNames = "aventura", key = "#id")
    public AventuraResponse getById(String id) {
        return appClient.getAventura(id);
    }

    /**
     * Tela de aventura: junta a aventura, seus caminhos e suas midias numa unica
     * resposta. O app faz UMA chamada em vez de tres sequenciais. Tudo vem do
     * APP (que concentra esses dados), entao continua sendo uma agregacao barata.
     */
    @Cacheable(cacheNames = "aventura-detalhe", key = "#id")
    public AventuraDetalheResponse getDetalhe(String id) {
        log.info("BFF: montando tela de aventura {}", id);
        AventuraResponse aventura = appClient.getAventura(id);
        List<CaminhoResponse> caminhos = appClient.getCaminhosByAventura(id, PRIMEIRA_PAGINA_DETALHE).conteudo();
        List<MidiaResponse> midias = appClient.getMidiasByAventura(id, PRIMEIRA_PAGINA_DETALHE).conteudo();
        return new AventuraDetalheResponse(aventura, caminhos, midias);
    }

    @Cacheable(cacheNames = "aventuras-usuario",
            key = "#usuarioId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<AventuraResponse> getByUsuario(String usuarioId, Pageable pageable) {
        return appClient.getAventurasByUsuario(usuarioId, pageable);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventura-detalhe", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public AventuraResponse atualizarStatus(String id, String status) {
        log.info("BFF: atualizando status da aventura {} para {}", id, status);
        return appClient.atualizarStatusAventura(id, status);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventura-detalhe", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public AventuraResponse moverRegiao(String id, MoverRegiaoRequest request) {
        log.info("BFF: movendo aventura {} de pasta", id);
        return appClient.moverRegiaoAventura(id, request);
    }

    @CacheEvict(cacheNames = "aventura", key = "#aventuraId")
    public void adicionarParticipante(String aventuraId, String usuarioId) {
        log.info("BFF: adicionando participante {} a aventura {}", usuarioId, aventuraId);
        appClient.adicionarParticipante(aventuraId, usuarioId);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventura-detalhe", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public void delete(String id) {
        log.info("BFF: deletando aventura {}", id);
        appClient.deletarAventura(id);
    }
}
