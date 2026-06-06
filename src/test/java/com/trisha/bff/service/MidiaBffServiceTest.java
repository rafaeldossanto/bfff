package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.MidiaResponse;
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
@DisplayName("MidiaBffService")
class MidiaBffServiceTest {

    @Mock
    private AppClient appClient;

    @InjectMocks
    private MidiaBffService service;

    @Test
    @DisplayName("salvar deve delegar ao AppClient")
    void deveSalvar() {
        when(appClient.salvarMidia(any())).thenReturn(BffStub.umaMidia());

        MidiaResponse response = service.salvar(BffStub.umaMidiaRequest());

        assertThat(response.url()).isEqualTo("https://cdn/midia.jpg");
        verify(appClient).salvarMidia(any());
    }

    @Test
    @DisplayName("getByAventura deve delegar e retornar lista")
    void deveListarPorAventura() {
        when(appClient.getMidiasByAventura(BffStub.AVENTURA_ID)).thenReturn(List.of(BffStub.umaMidia()));

        List<MidiaResponse> response = service.getByAventura(BffStub.AVENTURA_ID);

        assertThat(response).hasSize(1);
    }

    @Test
    @DisplayName("getByCaminho deve delegar e retornar lista")
    void deveListarPorCaminho() {
        when(appClient.getMidiasByCaminho(BffStub.CAMINHO_ID)).thenReturn(List.of(BffStub.umaMidia()));

        List<MidiaResponse> response = service.getByCaminho(BffStub.CAMINHO_ID);

        assertThat(response).hasSize(1);
    }

    @Test
    @DisplayName("delete deve delegar ao AppClient")
    void deveDeletar() {
        service.delete("midia-1");

        verify(appClient).deletarMidia("midia-1");
    }
}
