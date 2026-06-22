package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.DevLoginRequest;
import com.trisha.bff.model.dto.request.SocialLoginRequest;
import com.trisha.bff.model.dto.request.UserCreateRequest;
import com.trisha.bff.model.dto.request.UserUpdateRequest;
import com.trisha.bff.model.dto.response.AuthenticationResponse;
import com.trisha.bff.model.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class CadastroClient {

    private final RestClient cadastroRestClient;

    public CadastroClient(@Qualifier("cadastroRestClient") RestClient cadastroRestClient) {
        this.cadastroRestClient = cadastroRestClient;
    }

    public UserResponse create(UserCreateRequest request) {
        log.debug("CADASTRO: criando usuario {}", request.email());
        return cadastroRestClient.post()
                .uri("/usuario")
                .body(request)
                .retrieve()
                .body(UserResponse.class);
    }

    public UserResponse update(String id, UserUpdateRequest request) {
        log.debug("CADASTRO: atualizando usuario {}", id);
        return cadastroRestClient.put()
                .uri("/usuario/{id}", id)
                .body(request)
                .retrieve()
                .body(UserResponse.class);
    }

    public UserResponse getById(String id) {
        log.debug("CADASTRO: buscando usuario {}", id);
        return cadastroRestClient.get()
                .uri("/usuario/{id}", id)
                .retrieve()
                .body(UserResponse.class);
    }

    public void delete(String id) {
        log.debug("CADASTRO: deletando usuario {}", id);
        cadastroRestClient.delete()
                .uri("/usuario/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public String confirmEmail(String token) {
        log.debug("CADASTRO: confirmando email com token");
        return cadastroRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/auth/confirmar-email").queryParam("token", token).build())
                .retrieve()
                .body(String.class);
    }

    public String acceptTerms(String userId) {
        log.debug("CADASTRO: aceitando termos do usuario {}", userId);
        return cadastroRestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/aceitar-termos").queryParam("usuarioId", userId).build())
                .retrieve()
                .body(String.class);
    }

    public AuthenticationResponse socialLogin(SocialLoginRequest request) {
        log.debug("CADASTRO: login social via {}", request.provider());
        return cadastroRestClient.post()
                .uri("/auth/social")
                .body(request)
                .retrieve()
                .body(AuthenticationResponse.class);
    }

    public AuthenticationResponse devLogin(DevLoginRequest request) {
        log.debug("CADASTRO: dev login {}", request.email());
        return cadastroRestClient.post()
                .uri("/auth/dev-login")
                .body(request)
                .retrieve()
                .body(AuthenticationResponse.class);
    }
}
