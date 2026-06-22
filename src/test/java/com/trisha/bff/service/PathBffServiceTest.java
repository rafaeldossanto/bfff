package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.client.LocationClient;
import com.trisha.bff.model.dto.response.PathResponse;
import com.trisha.bff.model.dto.response.PageResponse;
import com.trisha.bff.model.dto.response.SessionResponse;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PathBffService")
class PathBffServiceTest {

    @Mock
    private AppClient appClient;
    @Mock
    private LocationClient locationClient;

    @InjectMocks
    private PathBffService service;

    @Test
    @DisplayName("start deve delegar ao AppClient")
    void deveIniciar() {
        when(appClient.startPath(any())).thenReturn(BffStub.aPath());

        PathResponse response = service.start(BffStub.aPathRequest());

        assertThat(response.id()).isEqualTo(BffStub.PATH_ID);
        verify(appClient).startPath(any());
    }

    @Test
    @DisplayName("finish deve finalizar a sessao no loc e usar a distancia real do GPS")
    void deveFinalizarComDistanciaDoLoc() {
        when(locationClient.getSessionByPath(BffStub.PATH_ID)).thenReturn(session("EM_ANDAMENTO", null));
        when(locationClient.finishSession("sessao-1")).thenReturn(session("FINALIZADA", 12.5));
        when(appClient.finishPath(BffStub.PATH_ID, 12.5)).thenReturn(BffStub.aPath());

        service.finish(BffStub.PATH_ID);

        verify(locationClient).finishSession("sessao-1");
        verify(appClient).finishPath(BffStub.PATH_ID, 12.5);
    }

    @Test
    @DisplayName("finish deve usar a distancia ja gravada quando a sessao ja estava finalizada")
    void deveUsarDistanciaQuandoSessaoJaFinalizada() {
        when(locationClient.getSessionByPath(BffStub.PATH_ID)).thenReturn(session("FINALIZADA", 8.0));
        when(appClient.finishPath(BffStub.PATH_ID, 8.0)).thenReturn(BffStub.aPath());

        service.finish(BffStub.PATH_ID);

        verify(locationClient, never()).finishSession(any());
        verify(appClient).finishPath(BffStub.PATH_ID, 8.0);
    }

    @Test
    @DisplayName("getByAdventure deve delegar e retornar pagina")
    void deveListarPorAventura() {
        Pageable pageable = PageRequest.of(0, 10);
        PageResponse<PathResponse> page =
                new PageResponse<>(List.of(BffStub.aPath()), 0, 10, 1L, 1);
        when(appClient.getPathsByAdventure(BffStub.ADVENTURE_ID, pageable)).thenReturn(page);

        PageResponse<PathResponse> response = service.getByAdventure(BffStub.ADVENTURE_ID, pageable);

        assertThat(response.content()).hasSize(1);
    }

    @Test
    @DisplayName("getByUser deve delegar e retornar pagina")
    void deveListarPorUsuario() {
        Pageable pageable = PageRequest.of(0, 10);
        PageResponse<PathResponse> page =
                new PageResponse<>(List.of(BffStub.aPath()), 0, 10, 1L, 1);
        when(appClient.getPathsByUser(BffStub.USER_ID, pageable)).thenReturn(page);

        PageResponse<PathResponse> response = service.getByUser(BffStub.USER_ID, pageable);

        assertThat(response.content()).hasSize(1);
    }

    private SessionResponse session(String status, Double totalDistanceKm) {
        return new SessionResponse("sessao-1", BffStub.PATH_ID, BffStub.USER_ID, status,
                false, 5.0, totalDistanceKm, null, null);
    }
}
