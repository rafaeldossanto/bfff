package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.client.CadastroClient;
import com.trisha.bff.model.dto.response.AuthenticationResponse;
import com.trisha.bff.model.dto.response.PublicUserResponse;
import com.trisha.bff.model.dto.response.UserResponse;
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
@DisplayName("UserBffService")
class UserBffServiceTest {

    @Mock
    private CadastroClient cadastroClient;

    @Mock
    private AppClient appClient;

    @InjectMocks
    private UserBffService service;

    @Test
    @DisplayName("create deve delegar ao CadastroClient")
    void deveCriar() {
        when(cadastroClient.create(any())).thenReturn(BffStub.aUser());

        UserResponse response = service.create(BffStub.aUserCreateRequest());

        assertThat(response.email()).isEqualTo("rafael@trilha.com");
        verify(cadastroClient).create(any());
    }

    @Test
    @DisplayName("update deve delegar ao CadastroClient")
    void deveAtualizar() {
        when(cadastroClient.update(BffStub.USER_ID, BffStub.aUserUpdateRequest()))
                .thenReturn(BffStub.aUser());

        service.update(BffStub.USER_ID, BffStub.aUserUpdateRequest());

        verify(cadastroClient).update(BffStub.USER_ID, BffStub.aUserUpdateRequest());
    }

    @Test
    @DisplayName("getById deve delegar ao CadastroClient")
    void deveBuscarPorId() {
        when(cadastroClient.getById(BffStub.USER_ID)).thenReturn(BffStub.aUser());

        UserResponse response = service.getById(BffStub.USER_ID);

        assertThat(response.id()).isEqualTo(BffStub.USER_ID);
    }

    @Test
    @DisplayName("delete deve delegar ao CadastroClient")
    void deveDeletar() {
        service.delete(BffStub.USER_ID);

        verify(cadastroClient).delete(BffStub.USER_ID);
    }

    @Test
    @DisplayName("acceptTerms deve delegar ao CadastroClient")
    void deveAceitarTermos() {
        when(cadastroClient.acceptTerms(BffStub.USER_ID)).thenReturn("Termos aceitos");

        String result = service.acceptTerms(BffStub.USER_ID);

        assertThat(result).isEqualTo("Termos aceitos");
    }

    @Test
    @DisplayName("socialLogin deve delegar ao CadastroClient")
    void deveFazerLoginSocial() {
        var request = new com.trisha.bff.model.dto.request.SocialLoginRequest("GOOGLE", "token-jwt");
        when(cadastroClient.socialLogin(request)).thenReturn(BffStub.anAuthentication());

        AuthenticationResponse response = service.socialLogin(request);

        assertThat(response.user().id()).isEqualTo(BffStub.USER_ID);
        assertThat(response.accessToken()).isEqualTo("jwt-token");
        verify(cadastroClient).socialLogin(request);
    }

    @Test
    @DisplayName("findByCode deve delegar ao AppClient")
    void deveBuscarPorCodigo() {
        var publico = new PublicUserResponse("rafael#1", "Rafael");
        when(appClient.findUserByCode("rafael#1")).thenReturn(publico);

        PublicUserResponse response = service.findByCode("rafael#1");

        assertThat(response.userCode()).isEqualTo("rafael#1");
        verify(appClient).findUserByCode("rafael#1");
    }

    @Test
    @DisplayName("autocomplete deve delegar ao AppClient")
    void deveAutocompletar() {
        when(appClient.autocompleteUser("raf"))
                .thenReturn(List.of(new PublicUserResponse("rafael#1", "Rafael")));

        List<PublicUserResponse> response = service.autocomplete("raf");

        assertThat(response).hasSize(1);
    }
}
