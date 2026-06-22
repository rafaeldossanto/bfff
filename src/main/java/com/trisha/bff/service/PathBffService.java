package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.client.LocationClient;
import com.trisha.bff.model.dto.request.PathRequest;
import com.trisha.bff.model.dto.response.PathResponse;
import com.trisha.bff.model.dto.response.PageResponse;
import com.trisha.bff.model.dto.response.SessionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PathBffService {

    private static final String STATUS_IN_PROGRESS = "EM_ANDAMENTO";

    private final AppClient appClient;
    private final LocationClient locationClient;

    public PathResponse start(PathRequest request) {
        log.info("BFF: iniciando caminho na aventura {}", request.adventureId());
        return appClient.startPath(request);
    }

    /**
     * Orquestra a finalizacao da trilha: busca a sessao de rastreamento do caminho
     * no servico de Localizacao, garante que ela esta finalizada (a distancia real
     * so e calculada na finalizacao da sessao) e usa ESSA distancia — a do GPS, nao
     * uma informada pelo cliente — para finalizar o caminho no APP. Mantem APP e
     * Localizacao desacoplados: o BFF e o maestro.
     */
    @CacheEvict(cacheNames = "caminhos-aventura", allEntries = true)
    public PathResponse finish(String id) {
        log.info("BFF: finalizando caminho {}", id);

        SessionResponse session = locationClient.getSessionByPath(id);
        if (STATUS_IN_PROGRESS.equals(session.status())) {
            session = locationClient.finishSession(session.id());
        }

        return appClient.finishPath(id, session.totalDistanceKm());
    }

    @Cacheable(cacheNames = "caminhos-aventura",
            key = "#adventureId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PageResponse<PathResponse> getByAdventure(String adventureId, Pageable pageable) {
        return appClient.getPathsByAdventure(adventureId, pageable);
    }

    @Cacheable(cacheNames = "caminhos-usuario",
            key = "#userId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PageResponse<PathResponse> getByUser(String userId, Pageable pageable) {
        return appClient.getPathsByUser(userId, pageable);
    }
}
