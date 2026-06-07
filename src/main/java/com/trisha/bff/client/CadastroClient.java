package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.LoginSocialRequest;
import com.trisha.bff.model.dto.request.UsuarioCreateRequest;
import com.trisha.bff.model.dto.request.UsuarioUpdateRequest;
import com.trisha.bff.model.dto.response.UsuarioResponse;
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

    public UsuarioResponse create(UsuarioCreateRequest request) {
        log.debug("CADASTRO: criando usuario {}", request.email());
        return cadastroRestClient.post()
                .uri("/usuario")
                .body(request)
                .retrieve()
                .body(UsuarioResponse.class);
    }

    public UsuarioResponse update(String id, UsuarioUpdateRequest request) {
        log.debug("CADASTRO: atualizando usuario {}", id);
        return cadastroRestClient.put()
                .uri("/usuario/{id}", id)
                .body(request)
                .retrieve()
                .body(UsuarioResponse.class);
    }

    public UsuarioResponse getById(String id) {
        log.debug("CADASTRO: buscando usuario {}", id);
        return cadastroRestClient.get()
                .uri("/usuario/{id}", id)
                .retrieve()
                .body(UsuarioResponse.class);
    }

    public void delete(String id) {
        log.debug("CADASTRO: deletando usuario {}", id);
        cadastroRestClient.delete()
                .uri("/usuario/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public String confirmarEmail(String token) {
        log.debug("CADASTRO: confirmando email com token");
        return cadastroRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/auth/confirmar-email").queryParam("token", token).build())
                .retrieve()
                .body(String.class);
    }

    public String aceitarTermos(String usuarioId) {
        log.debug("CADASTRO: aceitando termos do usuario {}", usuarioId);
        return cadastroRestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/aceitar-termos").queryParam("usuarioId", usuarioId).build())
                .retrieve()
                .body(String.class);
    }

    public UsuarioResponse loginSocial(LoginSocialRequest request) {
        log.debug("CADASTRO: login social via {}", request.provedor());
        return cadastroRestClient.post()
                .uri("/auth/social")
                .body(request)
                .retrieve()
                .body(UsuarioResponse.class);
    }
}
