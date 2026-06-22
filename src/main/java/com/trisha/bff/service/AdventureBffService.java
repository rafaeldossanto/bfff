package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.AdventureRequest;
import com.trisha.bff.model.dto.request.MoveRegionRequest;
import com.trisha.bff.model.dto.response.AdventureDetailResponse;
import com.trisha.bff.model.dto.response.AdventureResponse;
import com.trisha.bff.model.dto.response.PathResponse;
import com.trisha.bff.model.dto.response.MediaResponse;
import com.trisha.bff.model.dto.response.PageResponse;
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
public class AdventureBffService {

    /**
     * Pagina usada na agregacao da tela de aventura. A tela mostra os primeiros
     * caminhos e midias da aventura; o front pagina o resto via os endpoints
     * dedicados de caminho/midia. Mantemos um teto generoso para nao perder itens
     * na visao inicial sem trazer colecoes ilimitadas.
     */
    private static final Pageable FIRST_DETAIL_PAGE = PageRequest.of(0, 50);

    private final AppClient appClient;

    public AdventureResponse create(AdventureRequest request) {
        log.info("BFF: criando aventura para o destino {}", request.destination());
        return appClient.createAdventure(request);
    }

    @Cacheable(cacheNames = "aventura", key = "#id")
    public AdventureResponse getById(String id) {
        return appClient.getAdventure(id);
    }

    /**
     * Tela de aventura: junta a aventura, seus caminhos e suas midias numa unica
     * resposta. O app faz UMA chamada em vez de tres sequenciais. Tudo vem do
     * APP (que concentra esses dados), entao continua sendo uma agregacao barata.
     */
    @Cacheable(cacheNames = "aventura-detalhe", key = "#id")
    public AdventureDetailResponse getDetail(String id) {
        log.info("BFF: montando tela de aventura {}", id);
        AdventureResponse adventure = appClient.getAdventure(id);
        List<PathResponse> paths = appClient.getPathsByAdventure(id, FIRST_DETAIL_PAGE).content();
        List<MediaResponse> media = appClient.getMediaByAdventure(id, FIRST_DETAIL_PAGE).content();
        return new AdventureDetailResponse(adventure, paths, media);
    }

    @Cacheable(cacheNames = "aventuras-usuario",
            key = "#userId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PageResponse<AdventureResponse> getByUser(String userId, Pageable pageable) {
        return appClient.getAdventuresByUser(userId, pageable);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventura-detalhe", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public AdventureResponse updateStatus(String id, String status) {
        log.info("BFF: atualizando status da aventura {} para {}", id, status);
        return appClient.updateAdventureStatus(id, status);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventura-detalhe", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public AdventureResponse moveRegion(String id, MoveRegionRequest request) {
        log.info("BFF: movendo aventura {} de pasta", id);
        return appClient.moveAdventureRegion(id, request);
    }

    @CacheEvict(cacheNames = "aventura", key = "#adventureId")
    public void addParticipant(String adventureId, String userId) {
        log.info("BFF: adicionando participante {} a aventura {}", userId, adventureId);
        appClient.addParticipant(adventureId, userId);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventura-detalhe", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public void delete(String id) {
        log.info("BFF: deletando aventura {}", id);
        appClient.deleteAdventure(id);
    }
}
