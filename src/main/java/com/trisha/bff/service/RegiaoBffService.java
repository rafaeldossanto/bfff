package com.trisha.bff.service;

import com.trisha.bff.client.AppClient;
import com.trisha.bff.model.dto.response.RegiaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Regioes sao dados de referencia quase estaticos — cacheadas para o front
 * popular o seletor de criar aventura sem bater no APP a cada abertura.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegiaoBffService {

    private final AppClient appClient;

    @Cacheable(cacheNames = "regioes")
    public List<RegiaoResponse> listar() {
        log.info("BFF: listando regioes");
        return appClient.listarRegioes();
    }
}
