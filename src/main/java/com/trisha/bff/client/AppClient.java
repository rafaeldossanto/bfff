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
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.PontoInteresseResponse;
import com.trisha.bff.model.dto.response.RegiaoResponse;
import com.trisha.bff.model.dto.response.UsuarioPublicoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class AppClient {

    private static final ParameterizedTypeReference<PaginaResponse<AventuraResponse>> PAGINA_AVENTURA = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PaginaResponse<CaminhoResponse>> PAGINA_CAMINHO = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PaginaResponse<PontoInteresseResponse>> PAGINA_PONTO = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PaginaResponse<MidiaResponse>> PAGINA_MIDIA = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PaginaResponse<AmizadeResponse>> PAGINA_AMIZADE = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<UsuarioPublicoResponse>> LISTA_USUARIO_PUBLICO = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<RegiaoResponse>> LISTA_REGIAO = new ParameterizedTypeReference<>() {};

    private final RestClient appRestClient;

    public AppClient(@Qualifier("appRestClient") RestClient appRestClient) {
        this.appRestClient = appRestClient;
    }


    public List<RegiaoResponse> listarRegioes() {
        return appRestClient.get().uri("/regiao")
                .retrieve().body(LISTA_REGIAO);
    }

    public AventuraResponse criarAventura(AventuraRequest request) {
        return appRestClient.post().uri("/aventura").body(request)
                .retrieve().body(AventuraResponse.class);
    }

    public AventuraResponse getAventura(String id) {
        return appRestClient.get().uri("/aventura/{id}", id)
                .retrieve().body(AventuraResponse.class);
    }

    public PaginaResponse<AventuraResponse> getAventurasByUsuario(String usuarioId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/aventura/usuario/{usuarioId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(usuarioId))
                .retrieve().body(PAGINA_AVENTURA);
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

    public PaginaResponse<CaminhoResponse> getCaminhosByAventura(String aventuraId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/caminho/aventura/{aventuraId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(aventuraId))
                .retrieve().body(PAGINA_CAMINHO);
    }

    public PaginaResponse<CaminhoResponse> getCaminhosByUsuario(String usuarioId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/caminho/usuario/{usuarioId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(usuarioId))
                .retrieve().body(PAGINA_CAMINHO);
    }


    public PontoInteresseResponse criarPonto(PontoInteresseRequest request) {
        return appRestClient.post().uri("/ponto-interesse").body(request)
                .retrieve().body(PontoInteresseResponse.class);
    }

    public PontoInteresseResponse getPonto(String id) {
        return appRestClient.get().uri("/ponto-interesse/{id}", id)
                .retrieve().body(PontoInteresseResponse.class);
    }

    public PaginaResponse<PontoInteresseResponse> getPontosByCaminho(String caminhoId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/ponto-interesse/caminho/{caminhoId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(caminhoId))
                .retrieve().body(PAGINA_PONTO);
    }

    public EvidenciaResponse adicionarEvidencia(EvidenciaRequest request) {
        return appRestClient.post().uri("/ponto-interesse/evidencia").body(request)
                .retrieve().body(EvidenciaResponse.class);
    }


    public MidiaResponse salvarMidia(MidiaRequest request) {
        return appRestClient.post().uri("/midia").body(request)
                .retrieve().body(MidiaResponse.class);
    }

    public PaginaResponse<MidiaResponse> getMidiasByAventura(String aventuraId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/midia/aventura/{aventuraId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(aventuraId))
                .retrieve().body(PAGINA_MIDIA);
    }

    public PaginaResponse<MidiaResponse> getMidiasByCaminho(String caminhoId, Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/midia/caminho/{caminhoId}")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build(caminhoId))
                .retrieve().body(PAGINA_MIDIA);
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

    public PaginaResponse<AmizadeResponse> getPendentes(Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/amizade/pendentes")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve().body(PAGINA_AMIZADE);
    }

    public PaginaResponse<AmizadeResponse> getAmigos(Pageable pageable) {
        return appRestClient.get()
                .uri(b -> b.path("/amizade/amigos")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .retrieve().body(PAGINA_AMIZADE);
    }

    // --------------------------- Busca de usuario -----------------------

    public UsuarioPublicoResponse buscarUsuarioPorCodigo(String codigoUsuario) {
        return appRestClient.get().uri("/usuario/codigo/{codigo}", codigoUsuario)
                .retrieve().body(UsuarioPublicoResponse.class);
    }

    public List<UsuarioPublicoResponse> autocompletarUsuario(String termo) {
        return appRestClient.get()
                .uri(b -> b.path("/usuario/busca").queryParam("termo", termo).build())
                .retrieve().body(LISTA_USUARIO_PUBLICO);
    }
}
