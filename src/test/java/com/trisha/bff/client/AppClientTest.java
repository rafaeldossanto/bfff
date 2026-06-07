package com.trisha.bff.client;

import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Teste de unidade do AppClient usando MockRestServiceServer — simula o
 * downstream (servico APP) sem subir rede nem contexto Spring. Valida a rota
 * chamada, a desserializacao da resposta e a propagacao de erro HTTP.
 */
@DisplayName("AppClient")
class AppClientTest {

    private static final String BASE_URL = "http://localhost:8081";

    private MockRestServiceServer server;
    private AppClient appClient;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl(BASE_URL);
        server = MockRestServiceServer.bindTo(builder).build();
        appClient = new AppClient(builder.build());
    }

    @Test
    @DisplayName("getAmigos deve chamar a rota correta e desserializar a lista")
    void deveBuscarAmigos() {
        server.expect(requestTo(BASE_URL + "/amizade/amigos/usuario-1"))
                .andExpect(method(org.springframework.http.HttpMethod.GET))
                .andRespond(withSuccess("""
                        [{"id":"a1","solicitanteId":"usuario-1","receptorId":"usuario-2",
                          "status":"ACEITA","solicitadoEm":null,"respondidoEm":null}]
                        """, MediaType.APPLICATION_JSON));

        List<AmizadeResponse> amigos = appClient.getAmigos("usuario-1");

        assertThat(amigos).hasSize(1);
        assertThat(amigos.get(0).receptorId()).isEqualTo("usuario-2");
        server.verify();
    }

    @Test
    @DisplayName("getMidiasByAventura deve repassar page/size e desserializar a pagina")
    void deveBuscarMidias() {
        server.expect(requestTo(BASE_URL + "/midia/aventura/aventura-1?page=0&size=10"))
                .andExpect(method(org.springframework.http.HttpMethod.GET))
                .andRespond(withSuccess("""
                        {"content":[{"id":"m1","aventuraId":"aventura-1","caminhoId":"c1","tipo":"FOTO",
                          "url":"https://cdn/x.jpg","percentualNoCaminho":0.3,
                          "distanciaNaCapturaKm":1.5,"capturadaEm":null}],
                          "number":0,"size":10,"totalElements":1,"totalPages":1}
                        """, MediaType.APPLICATION_JSON));

        PaginaResponse<MidiaResponse> midias =
                appClient.getMidiasByAventura("aventura-1", PageRequest.of(0, 10));

        assertThat(midias.conteudo()).hasSize(1);
        assertThat(midias.conteudo().get(0).url()).isEqualTo("https://cdn/x.jpg");
        assertThat(midias.total()).isEqualTo(1L);
        server.verify();
    }

    @Test
    @DisplayName("erro 404 do downstream deve propagar como HttpClientErrorException")
    void devePropagarErroDownstream() {
        server.expect(requestTo(BASE_URL + "/amizade/amigos/inexistente"))
                .andRespond(withStatus(org.springframework.http.HttpStatus.NOT_FOUND));

        assertThatThrownBy(() -> appClient.getAmigos("inexistente"))
                .isInstanceOf(HttpClientErrorException.class);

        server.verify();
    }
}
