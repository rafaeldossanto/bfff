package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.CaminhoRequest;
import com.trisha.bff.model.dto.response.CaminhoResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
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

    private final AppClient appClient;

    public CaminhoResponse iniciar(CaminhoRequest request) {
        log.info("BFF: iniciando caminho na aventura {}", request.aventuraId());
        return appClient.iniciarCaminho(request);
    }

    @CacheEvict(cacheNames = "caminhos-aventura", allEntries = true)
    public CaminhoResponse finalizar(String id, Double distanciaTotalKm) {
        log.info("BFF: finalizando caminho {}", id);
        return appClient.finalizarCaminho(id, distanciaTotalKm);
    }

    @Cacheable(cacheNames = "caminhos-aventura",
            key = "#aventuraId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<CaminhoResponse> getByAventura(String aventuraId, Pageable pageable) {
        return appClient.getCaminhosByAventura(aventuraId, pageable);
    }

    @Cacheable(cacheNames = "caminhos-usuario",
            key = "#usuar