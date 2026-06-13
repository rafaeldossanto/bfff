package com.trisha.bff.service;

import com.trisha.bff.auth.UsuarioAutenticado;
import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.AmizadeRequest;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Orquestra operacoes de amizade sobre o servico APP. O ator vem do token
 * (propagado downstream); as listagens sao do proprio usuario autenticado.
 *
 * Escritas invalidam as listas inteiras (allEntries) — simples e seguro para um
 * dominio de baixo volume de escrita.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AmizadeBffService {

    private final AppClient appClient;

    @Caching(evict = {
            @CacheEvict(cacheNames = "amigos", allEntries = true),
            @CacheEvict(cacheNames = "amizades-pendentes", allEntries = true)
    })
    public AmizadeResponse solicitar(AmizadeRequest request) {
        log.info("BFF: solicitacao de amizade para {}", request.receptorCodigo());
        return appClient.solicitarAmizade(request);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "amigos", allEntries = true),
            @CacheEvict(cacheNames = "amizades-pendentes", allEntries = true)
    })
    public AmizadeResponse responder(String id, String status) {
        log.info("BFF: respondendo amizade {} com status {}", id, status);
        return appClient.responderAmizade(id, status);
    }

    @Cacheable(cacheNames = "amizades-pendentes",
            key = "#usuario.id + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<AmizadeResponse> getPendentes(UsuarioAutenticado usuario, Pageable pageable) {
        return appClient.getPendentes(pageable);
    }

    @Cacheable(cacheNames = "amigos",
            key = "#usuario.id + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginaResponse<AmizadeResponse> getAmigos(UsuarioAutenticado usuario, Pageable pageable) {
        return appClient.getAmigos(pageable);
    }
}
