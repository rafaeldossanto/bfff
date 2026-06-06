package com.trisha.bff.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente do microservico de Midia (binario no MinIO/R2).
 *
 * Nota arquitetural: para LEITURA de midias de uma aventura, o BFF bate no
 * APP — que ja mantem os metadados e a URL do binario. Este client e para os
 * casos em que o BFF precisa falar diretamente com o servico de Midia (ex.:
 * upload, geracao de presigned URL fresca, delete). Esqueleto pronto.
 */
@Component
@Slf4j
public class MidiaClient {

    private final RestClient midiaRestClient;

    public MidiaClient(@Qualifier("midiaRestClient") RestClient midiaRestClient) {
        this.midiaRestClient = midiaRestClient;
    }

    // Exemplo (preencher quando houver caso de uso):
    // public ArquivoResponse getById(String id) {
    //     return midiaRestClient.get()
    //             .uri("/arquivo/{id}", id)
    //             .retrieve()
    //             .body(ArquivoResponse.class);
    // }
}
