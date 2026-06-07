package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.client.CadastroClient;
import com.trisha.bff.model.dto.response.UsuarioPublicoResponse;
import com.trisha.bff.model.dto.response.UsuarioResponse;
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
@DisplayName("UsuarioBffService")
class UsuarioBffServiceTest {

    @Mock
    private CadastroClient cadastroClient;

    @Mock
    private AppClient appClient;

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

    @Test
    @DisplayName("loginSocial deve delegar ao CadastroClient")
    void deveFazerLoginSocial() {
        var request = new com.trisha.bff.model.dto.request.LoginSocialRequest("GOOGLE", "token-jwt");
        when(cadastroClient.loginSocial(request)).thenReturn(BffStub.umUsuario());

        UsuarioResponse response = service.loginSocial(request);

        assertThat(response.id()).isEqualTo(BffStub.USUARIO_ID);
        verify(cadastroClient).loginSocial(request);
    }

    @Test
    @DisplayName("buscarPorCodigo deve delegar ao AppClient")
    void deveBuscarPorCodigo() {
        var publico = new UsuarioPublicoResponse("rafael#1", "Rafael");
        when(appClient.buscarUsuarioPorCodigo("rafael#1")).thenReturn(publico);

        UsuarioPublicoResponse response = service.buscarPorCodigo("rafael#1");

        assertThat(response.codigoUsuario()).isEqualTo("rafael#1");
        verify(appClient).buscarUsuarioPorCodigo("rafael#1");
    }

    @Test
    @DisplayName("autocomplete deve delegar ao AppClient")
    void deveAutocompletar() {
        when(appClient.autocompletarUsuario("raf"))
                .thenReturn(List.of(new UsuarioPublicoResponse("rafael#1", "Rafael")));

        List<UsuarioPublicoResponse> response = service.autocomplete("raf");

        assertThat(response).hasSize(1);
    }
}
