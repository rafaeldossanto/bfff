package com.trisha.bff.client;

import com.trisha.bff.model.dto.request.AmizadeRequest;
import com.trisha.bff.model.dto.request.AventuraRequest;
import com.trisha.bff.model.dto.request.CaminhoRequest;
import com.trisha.bff.model.dto.request.EvidenciaRequest;
import com.trisha.bff.model.dto.request.MidiaRequest;
import com.trisha.bff.model.dto.request.PontoInteresseRequest;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.AventuraResponse;
import com.trisha.bff.model.dto.response.CaminhoResponse;
import com.trisha.bff.model.dto.response.EvidenciaResponse;
import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.PontoInteresseResponse;
import com.trisha.bff.model.dto.response.UsuarioPublicoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class AppClient {

    private static final ParameterizedTypeReference<PaginaResponse<AventuraResponse>> PAGINA_AVENTURA = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PaginaResponse<CaminhoResponse>> PAGINA_CAMINHO = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PaginaResponse<PontoInteresseResponse>> PAGINA_PONTO = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<PaginaResponse<MidiaResponse>> 