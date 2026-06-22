package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.GpsPointRequest;
import com.trisha.bff.model.dto.request.SessionRequest;
import com.trisha.bff.model.dto.response.GpsPointResponse;
import com.trisha.bff.model.dto.response.SessionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
@Slf4j
public class LocationClient {

    private static final ParameterizedTypeReference<List<GpsPointResponse>> LIST_POINT = new ParameterizedTypeReference<>() {};

    private final RestClient localizacaoRestClient;

    public LocationClient(@Qualifier("localizacaoRestClient") RestClient localizacaoRestClient) {
        this.localizacaoRestClient = localizacaoRestClient;
    }

    public SessionResponse startSession(SessionRequest request) {
        return localizacaoRestClient.post().uri("/localizacao/sessao").body(request)
                .retrieve().body(SessionResponse.class);
    }

    public GpsPointResponse registerPoint(GpsPointRequest request) {
        return localizacaoRestClient.post().uri("/localizacao/ponto").body(request)
                .retrieve().body(GpsPointResponse.class);
    }

    public SessionResponse finishSession(String id) {
        return localizacaoRestClient.patch().uri("/localizacao/sessao/{id}/finalizar", id)
                .retrieve().body(SessionResponse.class);
    }

    public SessionResponse cancelSession(String id) {
        return localizacaoRestClient.patch().uri("/localizacao/sessao/{id}/cancelar", id)
                .retrieve().body(SessionResponse.class);
    }

    public SessionResponse getSessionByPath(String pathId) {
        return localizacaoRestClient.get().uri("/localizacao/sessao/caminho/{caminhoId}", pathId)
                .retrieve().body(SessionResponse.class);
    }

    public List<GpsPointResponse> getPointsBySession(String sessionId) {
        return localizacaoRestClient.get().uri("/localizacao/pontos/sessao/{sessaoId}", sessionId)
                .retrieve().body(LIST_POINT);
    }

    public List<GpsPointResponse> getPointsByPath(String pathId) {
        return localizacaoRestClient.get().uri("/localizacao/pontos/caminho/{caminhoId}", pathId)
                .retrieve().body(LIST_POINT);
    }
}
