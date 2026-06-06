package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.EvidenciaRequest;
import com.trisha.bff.model.dto.request.PontoInteresseRequest;
import com.trisha.bff.model.dto.response.EvidenciaResponse;
import com.trisha.bff.model.dto.response.PontoInteresseResponse;
import com.trisha.bff.service.PontoInteresseBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bff/pontos-interesse")
@RequiredArgsConstructor
public class PontoInteresseController {

    private final PontoInteresseBffService pontoService;

    @PostMapping
    public PontoInteresseResponse create(@RequestBody @Valid PontoInteresseRequest request) {
        return pontoService.create(request);
    }

    @GetMapping("/{id}")
    public PontoInteresseResponse getById(@PathVariable String id) {
        return pontoService.getById(id);
    }

    @GetMapping("/caminho/{caminhoId}")
    public List<PontoInteresseResponse> getByCaminho(@PathVariable String caminhoId) {
        return pontoService.getByCaminho(caminhoId);
    }

    @PostMapping("/evidencia")
    public EvidenciaResponse adicionarEvidencia(@RequestBody @Valid EvidenciaRequest request) {
        return pontoService.adicionarEvidencia(request);
    }
}
