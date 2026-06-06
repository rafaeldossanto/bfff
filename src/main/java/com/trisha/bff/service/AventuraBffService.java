package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.AventuraRequest;
import com.trisha.bff.model.dto.response.AventuraResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    private final AppClient appClient;

    public AventuraResponse create(AventuraRequest request) {
        log.info("BFF: criando aventura para usuario {}", request.usuarioId());
        return appClient.criarAventura(request);
    }

    @Cacheable(cacheNames = "aventura", key = "#id")
    public AventuraResponse getById(String id) {
        return appClient.getAventura(id);
    }

    @Cacheable(cacheNames = "aventuras-usuario", key = "#usuarioId")
    public List<AventuraResponse> getByUsuario(String usuarioId) {
        return appClient.getAventurasByUsuario(usuarioId);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public AventuraResponse atualizarStatus(String id, String status) {
        log.info("BFF: atualizando status da aventura {} para {}", id, status);
        return appClient.atualizarStatusAventura(id, status);
    }

    @CacheEvict(cacheNames = "aventura", key = "#aventuraId")
    public void adicionarParticipante(String aventuraId, String usuarioId) {
        log.info("BFF: adicionando participante {} a aventura {}", usuarioId, aventuraId);
        appClient.adicionarParticipante(aventuraId, usuarioId);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "aventura", key = "#id"),
            @CacheEvict(cacheNames = "aventuras-usuario", allEntries = true)
    })
    public void delete(String id) {
        log.info("BFF: deletando aventura {}", id);
        appClient.deletarAventura(id);
    }
}
