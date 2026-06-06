package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.AmizadeResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Teste de unidade do AgregacaoService. Mocka o AppClient e valida a delegacao.
 * Nota: @Cacheable nao tem efeito em teste de unidade puro (depende do proxy
 * do Spring) — o comportamento de cache e coberto no teste de integracao.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AgregacaoService")
class AgregacaoServiceTest {

    @Mock
    private AppClient appClient;

    @InjectMocks
    private AgregacaoService service;

    @Test
    @DisplayName("getAmigos deve delegar ao AppClient e devolver a lista")
    void deveListarAmigos() {
        when(appClient.getAmigos(BffStub.USUARIO_ID)).thenReturn(List.of(BffStub.umaAmizade()));

        List<AmizadeResponse> response = service.getAmigos(BffStub.USUARIO_ID);

        assertThat(response).hasSize(1);
        assertThat(response.get(0).status()).isEqualTo("ACEITA");
        verify(appClient).getAmigos(BffStub.USUARIO_ID);
    }

    @Test
    @DisplayName("getMidiasDaAventura deve delegar ao AppClient e devolver a lista")
    void deveListarMidias() {
        when(appClient.getMidiasByAventura(BffStub.AVENTURA_ID)).thenReturn(List.of(BffStub.umaMidia()));

        List<MidiaResponse> response = service.getMidiasDaAventura(BffStub.AVENTURA_ID);

        assertThat(response).hasSize(1);
        assertThat(response.get(0).url()).isEqualTo("https://cdn/midia.jpg");
        verify(appClient).getMidiasByAventura(BffStub.AVENTURA_ID);
    }
}
