package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Orquestra as chamadas aos microservicos e devolve respostas prontas para o
 * front, com cache no Redis.
 *
 * Principio de roteamento: o BFF chama o MENOR numero de servicos possivel.
 * Ele so vira orquestrador multi-servico quando precisa juntar dados que
 * moram em servicos diferentes e nenhum deles tem a visao completa. Nos casos
 * abaixo, o APP ja concentra o que o front precisa (amizades e metadados de
 * midia, incluindo a URL do binario), entao uma chamada ao APP basta.
 *
 * O @Cacheable guarda o resultado no Redis pelo TTL configurado, aliviando os
 * downstreams em leituras repetidas. O nome do cache + a chave (o id) formam
 * a entrada; invalide com @CacheEvict nos fluxos de escrita correspondentes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AgregacaoService {

    private final AppClient appClient;

    /**
     * Lista de amigos de um usuario. Bate apenas no APP.
     */
    @Cacheable(cacheNames = "amigos", key = "#usuarioId")
    public List<AmizadeResponse> getAmigos(String usuarioId) {
        log.info("Agregando amigos do usuario {}", usuarioId);
        return appClient.getAmigos(usuarioId);
    }

    /**
     * Midias de uma aventura. Bate apenas no APP, que ja mantem a URL do
     * binario no storage — o front carrega as imagens direto dessas URLs,
     * sem o BFF precisar tocar no servico de Midia.
     */
    @Cacheable(cacheNames = "midias-aventura", key = "#aventuraId")
    public List<MidiaResponse> getMidiasDaAventura(String aventuraId) {
        log.info("Agregando midias da aventura {}", aventuraId);
        return appClient.getMidiasByAventura(aventuraId);
    }

    // ---------------------------------------------------------------------
    // Molde para agregacao multi-servico (usar quando um caso de uso exigir
    // juntar dados de 2+ downstreams). Exemplo: "dossie completo da aventura"
    // = dados do APP + trajeto GPS do Localizacao.
    //
    // @Cacheable(cacheNames = "dossie-aventura", key = "#aventuraId")
    // public DossieAventuraResponse getDossie(String aventuraId) {
    //     var midias  = appClient.getMidiasByAventura(aventuraId);
    //     var pontos  = localizacaoClient.getPontosByCaminho(...);
    //     return new DossieAventuraResponse(midias, pontos);
    // }
    // ---------------------------------------------------------------------
}
