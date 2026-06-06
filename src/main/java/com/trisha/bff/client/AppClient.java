package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.AmizadeRequest;
import com.trisha.bff.model.dto.request.AventuraRequest;
import com.trisha.bff.model.dto.request.CaminhoRequest;
import com.trisha.bff.model.dto.request.EvidenciaRequest;
import com.trisha.bff.model.dto.request.MidiaRequest;
import com.trisha.bff.model.dto.request.PontoInteresseRequest;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.AventuraResponse;
import com.trisha.bff.model.dto.response.CaminhoResponse;
import com.trisha.bff.model.dto.response.EvidenciaResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.model.dto.response.PontoInteresseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class AppClient {

    private static final ParameterizedTypeReference<List<AventuraResponse>> LISTA_AVENTURA = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<CaminhoResponse>> LISTA_CAMINHO = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<PontoInteresseResponse>> LISTA_PONTO = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<MidiaResponse>> LISTA_MIDIA = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<AmizadeResponse>> LISTA_AMIZADE = new ParameterizedTypeReference<>() {};

    private final RestClient appRestClient;

    public AppClient(@Qualifier("appRestClient") RestClient appRestClient) {
        this.appRestClient = appRestClient;
    }


    public AventuraResponse criarAventura(AventuraRequest request) {
        return appRestClient.post().uri("/aventura").body(request)
                .retrieve().body(AventuraResponse.class);
    }

    public AventuraResponse getAventura(String id) {
        return appRestClient.get().uri("/aventura/{id}", id)
                .retrieve().body(AventuraResponse.class);
    }

    public List<AventuraResponse> getAventurasByUsuario(String usuarioId) {
        return appRestClient.get().uri("/aventura/usuario/{usuarioId}", usuarioId)
                .retrieve().body(LISTA_AVENTURA);
    }

    public AventuraResponse atualizarStatusAventura(String id, String status) {
        return appRestClient.patch()
                .uri(b -> b.path("/aventura/{id}/status").queryParam("status", status).build(id))
                .retrieve().body(AventuraResponse.class);
    }

    public void adicionarParticipante(String aventuraId, String usuarioId) {
        appRestClient.post()
                .uri(b -> b.path("/aventura/{id}/participante").queryParam("usuarioId", usuarioId).build(aventuraId))
                .retrieve().toBodilessEntity();
    }

    public void deletarAventura(String id) {
        appRestClient.delete().uri("/aventura/{id}", id)
                .retrieve().toBodilessEntity();
    }

    public CaminhoResponse iniciarCaminho(CaminhoRequest request) {
        return appRestClient.post().uri("/caminho").body(request)
                .retrieve().body(CaminhoResponse.class);
    }

    public CaminhoResponse finalizarCaminho(String id, Double distanciaTotalKm) {
        return appRestClient.patch()
                .uri(b -> b.path("/caminho/{id}/finalizar").queryParam("distanciaTotalKm", distanciaTotalKm).build(id))
                .retrieve().body(CaminhoResponse.class);
    }

    public List<CaminhoResponse> getCaminhosByAventura(String aventuraId) {
        return appRestClient.get().uri("/caminho/aventura/{aventuraId}", aventuraId)
                .retrieve().body(LISTA_CAMINHO);
    }

    public List<CaminhoResponse> getCaminhosByUsuario(String usuarioId) {
        return appRestClient.get().uri("/caminho/usuario/{usuarioId}", usuarioId)
                .retrieve().body(LISTA_CAMINHO);
    }


    public PontoInteresseResponse criarPonto(PontoInteresseRequest request) {
        return appRestClient.post().uri("/ponto-interesse").body(request)
                .retrieve().body(PontoInteresseResponse.class);
    }

    public PontoInteresseResponse getPonto(String id) {
        return appRestClient.get().uri("/ponto-interesse/{id}", id)
                .retrieve().body(PontoInteresseResponse.class);
    }

    public List<PontoInteresseResponse> getPontosByCaminho(String caminhoId) {
        return appRestClient.get().uri("/ponto-interesse/caminho/{caminhoId}", caminhoId)
                .retrieve().body(LISTA_PONTO);
    }

    public EvidenciaResponse adicionarEvidencia(EvidenciaRequest request) {
        return appRestClient.post().uri("/ponto-interesse/evidencia").body(request)
                .retrieve().body(EvidenciaResponse.class);
    }


    public MidiaResponse salvarMidia(MidiaRequest request) {
        return appRestClient.post().uri("/midia").body(request)
                .retrieve().body(MidiaResponse.class);
    }

    public List<MidiaResponse> getMidiasByAventura(String aventuraId) {
        return appRestClient.get().uri("/midia/aventura/{aventuraId}", aventuraId)
                .retrieve().body(LISTA_MIDIA);
    }

    public List<MidiaResponse> getMidiasByCaminho(String caminhoId) {
        return appRestClient.get().uri("/midia/caminho/{caminhoId}", caminhoId)
                .retrieve().body(LISTA_MIDIA);
    }

    public void deletarMidia(String id) {
        appRestClient.delete().uri("/midia/{id}", id)
                .retrieve().toBodilessEntity();
    }


    public AmizadeResponse solicitarAmizade(AmizadeRequest request) {
        return appRestClient.post().uri("/amizade").body(request)
                .retrieve().body(AmizadeResponse.class);
    }

    public AmizadeResponse responderAmizade(String id, String status) {
        return appRestClient.patch()
                .uri(b -> b.path("/amizade/{id}/responder").queryParam("status", status).build(id))
                .retrieve().body(AmizadeResponse.class);
    }

    public List<AmizadeResponse> getPendentes(String usuarioId) {
        return appRestClient.get().uri("/amizade/pendentes/{usuarioId}", usuarioId)
                .retrieve().body(LISTA_AMIZADE);
    }

    public List<AmizadeResponse> getAmigos(String usuarioId) {
        return appRestClient.get().uri("/amizade/amigos/{usuarioId}", usuarioId)
                .retrieve().body(LISTA_AMIZADE);
    }
}
