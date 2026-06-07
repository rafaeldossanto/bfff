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
                .bod