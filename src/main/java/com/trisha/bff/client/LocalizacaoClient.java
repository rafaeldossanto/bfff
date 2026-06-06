package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.PontoGpsRequest;
import com.trisha.bff.model.dto.request.SessaoRequest;
import com.trisha.bff.model.dto.response.PontoGpsResponse;
import com.trisha.bff.model.dto.response.SessaoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
@Slf4j
public class LocalizacaoClient {

    private static final ParameterizedTypeReference<List<PontoGpsResponse>> LISTA_PONTO = new ParameterizedTypeReference<>() {};

    private final RestClient localizacaoRestClient;

    public LocalizacaoClient(@Qualifier("localizacaoRestClient") RestClient localizacaoRestClient) {
        this.localizacaoRestClient = localizacaoRestClient;
    }

    public SessaoResponse iniciarSessao(SessaoRequest request) {
        return localizacaoRestClient.post().uri("/localizacao/sessao").body(request)
                .retrieve().body(SessaoResponse.class);
    }

    public PontoGpsResponse registrarPonto(PontoGpsRequest request) {
        return localizacaoRestClient.post().uri("/localizacao/ponto").body(request)
                .retrieve().body(PontoGpsResponse.class);
    }

    public SessaoResponse finalizarSessao(String id) {
        return localizacaoRestClient.patch().uri("/localizacao/sessao/{id}/finalizar", id)
                .retrieve().body(SessaoResponse.class);
    }

    public SessaoResponse cancelarSessao(String id) {
        return localizacaoRestClient.patch().uri("/localizacao/sessao/{id}/cancelar", id)
                .retrieve().body(SessaoResponse.class);
    }

    public SessaoResponse getSessaoByCaminho(String caminhoId) {
        return localizacaoRestClient.get().uri("/localizacao/sessao/caminho/{caminhoId}", caminhoId)
                .retrieve().body(SessaoResponse.class);
    }

    public List<PontoGpsResponse> getPontosBySessao(String sessaoId) {
        return localizacaoRestClient.get().uri("/localizacao/pontos/sessao/{sessaoId}", sessaoId)
                .retrieve().body(LISTA_PONTO);
    }

    public List<PontoGpsResponse> getPontosByCaminho(String caminhoId) {
        return localizacaoRestClient.get().uri("/localizacao/pontos/caminho/{caminhoId}", caminhoId)
                .retrieve().body(LISTA_PONTO);
    }
}
