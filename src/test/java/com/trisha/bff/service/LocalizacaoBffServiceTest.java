package com.trisha.bff.service;

import com.trisha.bff.client.LocalizacaoClient;
import com.trisha.bff.model.dto.response.PontoGpsResponse;
import com.trisha.bff.model.dto.response.SessaoResponse;
import com.trisha.bff.stub.BffStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("LocalizacaoBffService")
class LocalizacaoBffServiceTest {

    @Mock
    private LocalizacaoClient localizacaoClient;

    @InjectMocks
    private LocalizacaoBffService service;

    @Test
    @DisplayName("iniciarSessao deve delegar ao LocalizacaoClient")
    void deveIniciarSessao() {
        when(localizacaoClient.iniciarSessao(any())).thenReturn(BffStub.umaSessao());

        SessaoResponse response = service.iniciarSessao(BffStub.umaSessaoRequest());

        assertThat(response.id()).isEqualTo(BffStub.SESSAO_ID);
        verify(localizacaoClient).iniciarSessao(any());
    }

    @Test
    @DisplayName("registrarPonto deve delegar ao LocalizacaoClient")
    void deveRegistrarPonto() {
        when(localizacaoClient.registrarPonto(any())).thenReturn(BffStub.umPontoGps());

        PontoGpsResponse response = service.registrarPonto(BffStub.umPontoGpsRequest());

        assertThat(response.ordem()).isEqualTo(1);
        verify(localizacaoClient).registrarPonto(any());
    }

    @Test
    @DisplayName("finalizarSessao deve delegar ao LocalizacaoClient")
    void deveFinalizarSessao() {
        when(localizacaoClient.finalizarSessao(BffStub.SESSAO_ID)).thenReturn(BffStub.umaSessao());

        service.finalizarSessao(BffStub.SESSAO_ID);

        verify(localizacaoClient).finalizarSessao(BffStub.SESSAO_ID);
    }

    @Test
    @DisplayName("cancelarSessao deve delegar ao LocalizacaoClient")
    void deveCancelarSessao() {
        when(localizacaoClient.cancelarSessao(BffStub.SESSAO_ID)).thenReturn(BffStub.umaSessao());

        service.cancelarSessao(BffStub.SESSAO_ID);

        verify(localizacaoClient).cancelarSessao(BffStub.SESSAO_ID);
    }

    @Test
    @DisplayName("getSessaoByCaminho deve delegar ao LocalizacaoClient")
    void deveBuscarSessaoPorCaminho() {
        when(localizacaoClient.getSessaoByCaminho(BffStub.CAMINHO_ID)).thenReturn(BffStub.umaSessao());

        SessaoResponse response = service.getSessaoByCaminho(BffStub.CAMINHO_ID);

        assertThat(response.caminhoId()).isEqualTo(BffStub.CAMINHO_ID);
    }

    @Test
    @DisplayName("getPontosBySessao deve delegar e retornar lista")
    void deveListarPontosPorSessao() {
        when(localizacaoClient.getPontosBySessao(BffStub.SESSAO_ID)).thenReturn(List.of(BffStub.umPontoGps()));

        List<PontoGpsResponse> response = service.getPontosBySessao(BffStub.SESSAO_ID);

        assertThat(response).hasSize(1);
    }

    @Test
    @DisplayName("getPontosByCaminho deve delegar e retornar lista")
    void deveListarPontosPorCaminho() {
        when(localizacaoClient.getPontosByCaminho(BffStub.CAMINHO_ID)).thenReturn(List.of(BffStub.umPontoGps()));

        List<PontoGpsResponse> response = service.getPontosByCaminho(BffStub.CAMINHO_ID);

        assertThat(response).hasSize(1);
    }
}
