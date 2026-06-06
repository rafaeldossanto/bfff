package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.AmizadeResponse;
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
@DisplayName("AmizadeBffService")
class AmizadeBffServiceTest {

    @Mock
    private AppClient appClient;

    @InjectMocks
    private AmizadeBffService service;

    @Test
    @DisplayName("solicitar deve delegar ao AppClient")
    void deveSolicitar() {
        when(appClient.solicitarAmizade(any())).thenReturn(BffStub.umaAmizade());

        AmizadeResponse response = service.solicitar(BffStub.umaAmizadeRequest());

        assertThat(response.id()).isEqualTo("amizade-1");
        verify(appClient).solicitarAmizade(any());
    }

    @Test
    @DisplayName("responder deve delegar com o status")
    void deveResponder() {
        when(appClient.responderAmizade("amizade-1", "ACEITA")).thenReturn(BffStub.umaAmizade());

        service.responder("amizade-1", "ACEITA");

        verify(appClient).responderAmizade("amizade-1", "ACEITA");
    }

    @Test
    @DisplayName("getPendentes deve delegar e retornar lista")
    void deveListarPendentes() {
        when(appClient.getPendentes(BffStub.USUARIO_ID)).thenReturn(List.of(BffStub.umaAmizade()));

        List<AmizadeResponse> response = service.getPendentes(BffStub.USUARIO_ID);

        assertThat(response).hasSize(1);
    }

    @Test
    @DisplayName("getAmigos deve delegar e retornar lista")
    void deveListarAmigos() {
        when(appClient.getAmigos(BffStub.USUARIO_ID)).thenReturn(List.of(BffStub.umaAmizade()));

        List<AmizadeResponse> response = service.getAmigos(BffStub.USUARIO_ID);

        assertThat(response).hasSize(1);
    }
}
