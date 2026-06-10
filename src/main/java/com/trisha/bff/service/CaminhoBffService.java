package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.client.LocalizacaoClient;
import com.trisha.bff.model.dto.request.CaminhoRequest;
import com.trisha.bff.model.dto.response.CaminhoResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.SessaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CaminhoBffService {

    private static final String STATUS_EM_ANDAMENTO = "EM_ANDAMENTO";

    private final AppClient appClient;
    private final LocalizacaoClient localizacaoClient;

    public CaminhoResponse iniciar(CaminhoRequest request) {
        log.info("BFF: iniciando caminho na aventura {}", request.aventuraId());
        return appClient.iniciarCaminho(request);
    }

    /**
     * Orquestra a finalizacao da trilha: busca a sessao de rastreamento do caminho
     * no servico de Localizacao, garante que ela esta finalizada (a distancia real
     * so e calculada na finalizacao da sessao) e usa ESSA distancia — a do GPS, nao
     * uma informada pelo cliente — para finalizar o caminho no APP. Mantem APP e
     * Localizacao desacoplados: o BFF e o maestro.
     */
    @CacheEvict(cacheNames = "caminhos-aventura", allEntries = true)
    public CaminhoResponse finalizar(String id) {
        log.info("BFF: finalizando caminho {}", id);

        SessaoResponse sessao = localizacaoClient.getSessaoByCaminho(id);
        if (STATUS_EM_ANDAMENTO.equals(sessao.status())) {
            sessao = localizacaoClient.finalizarSessao(sessao.id());
        }

        return appClient.finalizarCaminho(id, sessao.distanciaTotalKm());
    }

    @Cacheable(cacheNames = "caminhos-aventura",
            key = "#aventuraId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<CaminhoResponse> getByAventura(String aventuraId, Pageable pageable) {
        return appClient.getCaminhosByAventura(aventuraId, pageable);
    }

    @Cacheable(cacheNames = "caminhos-usuario",
            key = "#usuarioId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<CaminhoResponse> getByUsuario(String usuarioId, Pageable pageable) {
        return appClient.getCaminhosByUsuario(usuarioId, pageable);
    }
}
