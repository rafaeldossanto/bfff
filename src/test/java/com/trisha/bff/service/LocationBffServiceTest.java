package com.trisha.bff.service;

import com.trisha.bff.client.LocationClient;
import com.trisha.bff.model.dto.response.GpsPointResponse;
import com.trisha.bff.model.dto.response.SessionResponse;
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
@DisplayName("LocationBffService")
class LocationBffServiceTest {

    @Mock
    private LocationClient locationClient;

    @InjectMocks
    private LocationBffService service;

    @Test
    @DisplayName("startSession deve delegar ao LocationClient")
    void deveIniciarSessao() {
        when(locationClient.startSession(any())).thenReturn(BffStub.aSession());

        SessionResponse response = service.startSession(BffStub.aSessionRequest());

        assertThat(response.id()).isEqualTo(BffStub.SESSION_ID);
        verify(locationClient).startSession(any());
    }

    @Test
    @DisplayName("registerPoint deve delegar ao LocationClient")
    void deveRegistrarPonto() {
        when(locationClient.registerPoint(any())).thenReturn(BffStub.aGpsPoint());

        GpsPointResponse response = service.registerPoint(BffStub.aGpsPointRequest());

        assertThat(response.order()).isEqualTo(1);
        verify(locationClient).registerPoint(any());
    }

    @Test
    @DisplayName("finishSession deve delegar ao LocationClient")
    void deveFinalizarSessao() {
        when(locationClient.finishSession(BffStub.SESSION_ID)).thenReturn(BffStub.aSession());

        service.finishSession(BffStub.SESSION_ID);

        verify(locationClient).finishSession(BffStub.SESSION_ID);
    }

    @Test
    @DisplayName("cancelSession deve delegar ao LocationClient")
    void deveCancelarSessao() {
        when(locationClient.cancelSession(BffStub.SESSION_ID)).thenReturn(BffStub.aSession());

        service.cancelSession(BffStub.SESSION_ID);

        verify(locationClient).cancelSession(BffStub.SESSION_ID);
    }

    @Test
    @DisplayName("getSessionByPath deve delegar ao LocationClient")
    void deveBuscarSessaoPorCaminho() {
        when(locationClient.getSessionByPath(BffStub.PATH_ID)).thenReturn(BffStub.aSession());

        SessionResponse response = service.getSessionByPath(BffStub.PATH_ID);

        assertThat(response.pathId()).isEqualTo(BffStub.PATH_ID);
    }

    @Test
    @DisplayName("getPointsBySession deve delegar e retornar lista")
    void deveListarPontosPorSessao() {
        when(locationClient.getPointsBySession(BffStub.SESSION_ID)).thenReturn(List.of(BffStub.aGpsPoint()));

        List<GpsPointResponse> response = service.getPointsBySession(BffStub.SESSION_ID);

        assertThat(response).hasSize(1);
    }

    @Test
    @DisplayName("getPointsByPath deve delegar e retornar lista")
    void deveListarPontosPorCaminho() {
        when(locationClient.getPointsByPath(BffStub.PATH_ID)).thenReturn(List.of(BffStub.aGpsPoint()));

        List<GpsPointResponse> response = service.getPointsByPath(BffStub.PATH_ID);

        assertThat(response).hasSize(1);
    }
}
