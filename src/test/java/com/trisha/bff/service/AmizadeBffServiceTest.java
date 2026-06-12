package com.trisha.bff.service;

import com.trisha.bff.auth.UsuarioAutenticado;
import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
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
@DisplayName("AmizadeBffService")
class AmizadeBffServiceTest {

    @Mock
    private AppClient appClient;

    @InjectMocks
    private AmizadeBffService service;

    private final UsuarioAutenticado usuario = new UsuarioAutenticado(BffStub.USUARIO_ID, "rafael#1", "rafael@trilha.com");
    private final Pageable pageable = PageRequest.of(0, 10);

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
    @DisplayName("getPendentes deve delegar e retornar pagina")
    void deveListarPendentes() {
        PaginaResponse<AmizadeResponse> pagina = new PaginaResponse<>(List.of(BffStub.umaAmizade()), 0, 10, 1L, 1);
        when(appClient.getPendentes(pageable)).thenReturn(pagina);

        PaginaResponse<AmizadeResponse> response = service.getPendentes(usuario, pageable);

        assertThat(response.conteudo()).hasSize(1);
    }

    @Test
    @DisplayName("getAmigos deve delegar e retornar pagina")
    void deveListarAmigos() {
        PaginaResponse<AmizadeResponse> pagina = new PaginaResponse<>(List.of(BffStub.umaAmizade()), 0, 10, 1L, 1);
        when(appClient.getAmigos(pageable)).thenReturn(pagina);

        PaginaResponse<AmizadeResponse> response = service.getAmigos(usuario, pageable);

        assertThat(response.conteudo()).hasSize(1);
    }
}
