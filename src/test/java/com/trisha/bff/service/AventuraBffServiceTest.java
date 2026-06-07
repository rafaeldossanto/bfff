package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.AventuraResponse;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AventuraBffService")
class AventuraBffServiceTest {

    @Mock
    private AppClient appClient;

    @InjectMocks
    private AventuraBffService service;

    @Test
    @DisplayName("create deve delegar ao AppClient")
    void deveCriar() {
        when(appClient.criarAventura(any())).thenReturn(BffStub.umaAventura());

        AventuraResponse response = service.create(BffStub.umaAventuraRequest());

        assertThat(response.id()).isEqualTo(BffStub.AVENTURA_ID);
        verify(appClient).criarAventura(any());
    }

    @Test
    @DisplayName("getDetalhe deve agregar aventura, caminhos e midias")
    void deveMontarDetalhe() {
        when(appClient.getAventura(BffStub.AVENTURA_ID)).thenReturn(BffStub.umaAventura());
        when(appClient.getCaminhosByAventura(eq(BffStub.AVENTURA_ID), any()))
                .thenReturn(new PaginaResponse<>(List.of(BffStub.umCaminho()), 0, 50, 1L, 1));
        when(appClient.getMidiasByAventura(eq(BffStub.AVENTURA_ID), any()))
                .thenReturn(new PaginaResponse<>(List.of(BffStub.umaMidia()), 0, 50, 1L, 1));

        var detalhe = service.getDetalhe(BffStub.AVENTURA_ID);

        assertThat(detalhe.aventura().id()).isEqualTo(BffStub.AVENTURA_ID);
        assertThat(detalhe.caminhos()).hasSize(1);
        assertThat(detalhe.midias()).hasSize(1);
    }

    @Test
    @DisplayName("getById deve delegar ao AppClient")
    void deveBuscarPorId() {
        when(appClient.getAventura(BffStub.AVENTURA_ID)).thenReturn(BffStub.umaAventura());

        AventuraResponse response = service.getById(BffStub.AVENTURA_ID);

        assertThat(response.destino()).isEqualTo("Pico da Bandeira");
    }

    @Test
    @DisplayName("getByUsuario deve delegar e retornar pagina")
    void deveListarPorUsuario() {
        Pageable pageable = PageRequest.of(0, 10);
        PaginaResponse<AventuraResponse> pagina =
                new PaginaResponse<>(List.of(BffStub.umaAventura()), 0, 10, 1L, 1);
        when(appClient.getAventurasByUsuario(BffStub.USUARIO_ID, pageable)).thenReturn(pagina);

        PaginaResponse<AventuraResponse> response = service.getByUsuario(BffStub.USUARIO_ID, pageable);

        assertThat(response.conteudo()).hasSize(1);
        assertThat(response.total()).isEqualTo(1L);
    }

    @Test
    @DisplayName("atualizarStatus deve delegar ao AppClient")
    void deveAtualizarStatus() {
        when(appClient.atualizarStatusAventura(BffStub.AVENTURA_ID, "CONCLUIDA"))
                .thenReturn(BffStub.umaAventura());

        service.atualizarStatus(BffStub.AVENTURA_ID, "CONCLUIDA");

        verify(appClient).atualizarStatusAventura(BffStub.AVENTURA_ID, "CONCLUIDA");
    }

    @Test
    @DisplayName("adicionarParticipante deve delegar ao AppClient")
    void deveAdicionarParticipante() {
        service.adicionarParticipante(BffStub.AVENTURA_ID, "usuario-9");

        verify(appClient).adicionarParticipante(BffStub.AVENTURA_ID, "usuario-9");
    }

    @Test
    @DisplayName("delete deve delegar ao AppClient")
    void deveDeletar() {
        service.delete(BffStub.AVENTURA_ID);

        verify(appClient).deletarAventura(BffStub.AVENTURA_ID);
    }
}
