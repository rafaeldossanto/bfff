package com.trisha.bff.controller;

import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.service.AgregacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints do BFF relacionados a midias de aventuras. Retorna o DTO direto.
 */
@RestController
@RequestMapping("/bff/midias")
@RequiredArgsConstructor
public class MidiaController {

    private final AgregacaoService agregacaoService;

    @GetMapping("/aventura/{aventuraId}")
    public List<MidiaResponse> getMidiasDaAventura(@PathVariable String aventuraId) {
        return agregacaoService.getMidiasDaAventura(aventuraId);
    }
}
