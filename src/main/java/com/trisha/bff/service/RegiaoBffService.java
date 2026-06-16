package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.RegiaoRequest;
import com.trisha.bff.model.dto.response.AventuraResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.RegiaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Orquestra as regioes (pastas) sobre o servico APP. Sem cache: regiao agora e
 * dado por usuario e mutavel (CRUD), entao seria mais risco de stale do que ganho.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegiaoBffService {

    private final AppClient appClient;

    public RegiaoResponse criar(RegiaoRequest request) {
        log.info("BFF: criando regiao {}", request.nome());
        return appClient.criarRegiao(request);
    }

    public PaginaResponse<RegiaoResponse> minhas(Pageable pageable) {
        return appClient.getMinhasRegioes(pageable);
    }

    public PaginaResponse<RegiaoResponse> descobrir(Pageable pageable) {
        return appClient.descobrirRegioes(pageable);
    }

    public RegiaoResponse getById(String id) {
        return appClient.getRegiao(id);
    }

    public PaginaResponse<AventuraResponse> aventuras(String id, Pageable pageable) {
        return appClient.getAventurasByRegiao(id, pageable);
    }

    public RegiaoResponse atualizar(String id, RegiaoRequest request) {
        return appClient.atualizarRegiao(id, request);
    }

    public void deletar(String id) {
        log.info("BFF: apagando regiao {}", id);
        appClient.deletarRegiao(id);
    }
}
