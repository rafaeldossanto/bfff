package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.request.AventuraRequest;
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
        log.info("BFF: criando aventura para usuario {}", request.usuarioId());
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
        AventuraResponse