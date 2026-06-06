package com.trisha.bff.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente do microservico de Cadastro (usuarios, confirmacao de email,
 * fluxo Camunda). Esqueleto pronto — preencher conforme o BFF expor
 * operacoes de cadastro ao front.
 */
@Component
@Slf4j
public class CadastroClient {

    private final RestClient cadastroRestClient;

    public CadastroClient(@Qualifier("cadastroRestClient") RestClient cadastroRestClient) {
        this.cadastroRestClient = cadastroRestClient;
    }

    // Exemplo (preencher quando houver caso de uso):
    // public UsuarioResponse getById(String id) {
    //     return cadastroRestClient.get()
    //             .uri("/usuario/{id}", id)
    //             .retrieve()
    //             .body(UsuarioResponse.class);
    // }
}
