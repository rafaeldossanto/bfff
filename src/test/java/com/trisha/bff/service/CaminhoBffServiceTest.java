package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.CaminhoResponse;
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
@DisplayName("CaminhoBffService")
class CaminhoBffServiceTest {

    @Mock
    private AppClient appClient;

    @InjectMocks
    private CaminhoBffService service;

    @Test
    @DisplayName("iniciar deve delegar ao AppClient")
    void deveIniciar() {
        when(appClient.iniciarCaminho(any())).thenReturn(BffStub.umCaminho());

        CaminhoResponse response = service.iniciar(BffStub.umCaminhoRequest());

        assertThat(response.id()).isEqualTo(BffStub.CAMINHO_ID);
        verify(appClient).iniciarCaminho(any());
    }

    @Test
    @DisplayName("finalizar deve delegar com a distancia")
    void deveFinalizar() {
        when(appClient.finalizarCaminho(BffStub.CAMINHO_ID, 12.5)).thenReturn(BffStub.umCaminho());

        service.finalizar(BffStub.CAMINHO_ID, 12.5);

        verify(appClient).finalizarCaminho(BffStub.CAMINHO_ID, 12.5);
    }

    @Test
    @DisplayName("getByAventura deve delegar e retornar lista")
    void deveListarPorAventura() {
        when(appClient.getCaminhosByAventura(BffStub.AVENTURA_ID)).thenReturn(List.of(BffStub.umCaminho()));

        List<CaminhoResponse> response = service.getByAventura(BffStub.AVENTURA_ID);

        assertThat(response).hasSize(1);
    }

    @Test
    @DisplayName("getByUsuario deve delegar e retornar lista")
    void deveListarPorUsuario() {
        when(appClient.getCaminhosByUsuario(BffStub.USUARIO_ID)).thenReturn(List.of(BffStub.umCaminho()));

        List<CaminhoResponse> response = service.getByUsuario(BffStub.USUARIO_ID);

        assertThat(response).hasSize(1);
    }
}
