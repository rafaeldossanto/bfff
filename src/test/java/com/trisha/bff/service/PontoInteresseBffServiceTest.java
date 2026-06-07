package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.EvidenciaResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.PontoInteresseResponse;
import com.trisha.bff.stub.BffStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PontoInteresseBffService")
class PontoInteresseBffServiceTest {

    @Mock
    private AppClient appClient;

    @InjectMocks
    private PontoInteresseBffService service;

    @Test
    @DisplayName("create deve delegar ao AppClient")
    void deveCriar() {
        when(appClient.criarPonto(any())).thenReturn(BffStub.umPonto());

        PontoInteresseResponse response = service.create(BffStub.umPontoRequest());

        assertThat(response.id()).isEqualTo(BffStub.PONTO_ID);
        verify(appClient).criarPonto(any());
    }

    @Test
    @DisplayName("getById deve delegar ao AppClient")
    void deveBuscarPorId() {
        when(appClient.getPonto(BffStub.PONTO_ID)).thenReturn(BffStub.umPonto());

        PontoInteresseResponse response = service.getById(BffStub.PONTO_ID);

        assertThat(response.nivelConfianca()).isEqualTo(3);
    }

    @Test
    @DisplayName("getByCaminho deve delegar e retornar pagina")
    void deveListarPorCaminho() {
        Pageable pageable = PageRequest.of(0, 10);
        PaginaResponse<PontoInteresseResponse> pagina =
                new PaginaResponse<>(List.of(BffStub.umPonto()), 0, 10, 1L, 1);
        when(appClient.getPontosByCaminho(BffStub.CAMINHO_ID, pageable)).thenReturn(pagina);

        PaginaResponse<PontoInteresseResponse> response = service.getByCaminho(BffStub.CAMINHO_ID, pageable);

        assertThat(response.conteudo()).hasSize(1);
    }

    @Test
    @DisplayName("adicionarEvidencia deve delegar ao AppClient")
    void deveAdicionarEvidencia() {
        when(appClient.adicionarEvidencia(any())).thenReturn(BffStub.umaEvidencia());

        EvidenciaResponse response = service.adicionarEvidencia(BffStub.umaEvidenciaRequest());

        assertThat(response.validada()).isTrue();
        verify(appClient).adicionarEvidencia(any());
    }
}
