package com.trisha.bff.client;

import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Cliente do microservico APP. Encapsula as chamadas HTTP para que os services
 * de agregacao nao conhecam detalhes de transporte (rotas, RestClient). Erros
 * HTTP do downstream sobem como HttpClientErrorException/HttpServerErrorException
 * e sao tratados centralmente no GlobalExceptionHandler.
 */
@Component
@Slf4j
public class AppClient {

    private final RestClient appRestClient;

    public AppClient(@Qualifier("appRestClient") RestClient appRestClient) {
        this.appRestClient = appRestClient;
    }

    /** Lista os amigos (amizades aceitas) de um usuario. */
    public List<AmizadeResponse> getAmigos(String usuarioId) {
        log.debug("APP: buscando amigos do usuario {}", usuarioId);
        return appRestClient.get()
                .uri("/amizade/amigos/{usuarioId}", usuarioId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    /** Lista as midias (metadados + URL) de uma aventura. */
    public List<MidiaResponse> getMidiasByAventura(String aventuraId) {
        log.debug("APP: buscando midias da aventura {}", aventuraId);
        return appRestClient.get()
                .uri("/midia/aventura/{aventuraId}", aventuraId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
