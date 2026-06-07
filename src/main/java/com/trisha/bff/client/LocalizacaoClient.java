package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.PontoGpsRequest;
import com.trisha.bff.model.dto.request.SessaoRequest;
import com.trisha.bff.model.dto.response.PontoGpsResponse;
import com.trisha.bff.model.dto.response.SessaoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
@Slf4j
public class LocalizacaoClient {

    private static final ParameterizedTypeReference<List<PontoGpsResponse>> LISTA_PONTO = new ParameterizedTypeReference<>() {};

    private final RestClient localizacaoRestClient;

    public LocalizacaoClient(@Qualifier("localizacaoRestClient") RestClient localizacaoRestClient) {
        this.localizacaoRestClient = localizacaoRestClient;
    }

    public SessaoResponse iniciarSessao(SessaoRequest request) {
        return localizacaoRestClient.post().uri("/localizacao/sessao").body(request)
                .retrieve().body(SessaoResp