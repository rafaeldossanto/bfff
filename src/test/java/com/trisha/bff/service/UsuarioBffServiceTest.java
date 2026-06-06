package com.trisha.bff.service;

import com.trisha.bff.client.CadastroClient;
import com.trisha.bff.model.dto.response.UsuarioResponse;
import com.trisha.bff.stub.BffStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioBffService")
class UsuarioBffServiceTest {

    @Mock
    private CadastroClient cadastroClient;

    @InjectMocks
    private UsuarioBffService service;

    @Test
    @DisplayName("create deve delegar ao CadastroClient")
    void deveCriar() {
        when(cadastroClient.create(any())).thenReturn(BffStub.umUsuario());

        UsuarioResponse response = service.create(BffStub.umUsuarioCreateRequest());

        assertThat(response.email()).isEqualTo("rafael@trilha.com");
        verify(cadastroClient).create(any());
    }

    @Test
    @DisplayName("update deve delegar ao CadastroClient")
    void deveAtualizar() {
        when(cadastroClient.update(BffStub.USUARIO_ID, BffStub.umUsuarioUpdateRequest()))
                .thenReturn(BffStub.umUsuario());

        service.update(BffStub.USUARIO_ID, BffStub.umUsuarioUpdateRequest());

        verify(cadastroClient).update(BffStub.USUARIO_ID, BffStub.umUsuarioUpdateRequest());
    }

    @Test
    @DisplayName("getById deve delegar ao CadastroClient")
    void deveBuscarPorId() {
        when(cadastroClient.getById(BffStub.USUARIO_ID)).thenReturn(BffStub.umUsuario());

        UsuarioResponse response = service.getById(BffStub.USUARIO_ID);

        assertThat(response.id()).isEqualTo(BffStub.USUARIO_ID);
    }

    @Test
    @DisplayName("delete deve delegar ao CadastroClient")
    void deveDeletar() {
        service.delete(BffStub.USUARIO_ID);

        verify(cadastroClient).delete(BffStub.USUARIO_ID);
    }

    @Test
    @DisplayName("aceitarTermos deve delegar ao CadastroClient")
    void deveAceitarTermos() {
        when(cadastroClient.aceitarTermos(BffStub.USUARIO_ID)).thenReturn("Termos aceitos");

        String resultado = service.aceitarTermos(BffStub.USUARIO_ID);

        assertThat(resultado).isEqualTo("Termos aceitos");
    }
}
