package com.trisha.bff.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente do microservico de Localizacao (GPS / sessoes de rastreamento).
 * Esqueleto pronto para receber metodos conforme o BFF precisar agregar
 * dados de trajeto. Use o padrao do {@link AppClient}: um metodo por
 * chamada, retornando o DTO de resposta do BFF.
 */
@Component
@Slf4j
public class LocalizacaoClient {

    private final RestClient localizacaoRestClient;

    public LocalizacaoClient(@Qualifier("localizacaoRestClient") RestClient localizacaoRestClient) {
        this.localizacaoRestClient = localizacaoRestClient;
    }

    // Exemplo (preencher quando houver caso de uso):
    // public List<PontoGpsResponse> getPontosByCaminho(String caminhoId) {
    //     return localizacaoRestClient.get()
    //             .uri("/localizacao/caminho/{caminhoId}/pontos", caminhoId)
    //             .retrieve()
    //             .body(new ParameterizedTypeReference<>() {});
    // }
}
