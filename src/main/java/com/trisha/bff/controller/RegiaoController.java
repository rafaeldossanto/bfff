package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.RegiaoRequest;
import com.trisha.bff.model.dto.response.AventuraResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.RegiaoResponse;
import com.trisha.bff.service.RegiaoBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/regioes")
@RequiredArgsConstructor
public class RegiaoController {

    private final RegiaoBffService regiaoService;

    @PostMapping
    public RegiaoResponse criar(@RequestBody @Valid RegiaoRequest request) {
        return regiaoService.criar(request);
    }

    @GetMapping
    public PaginaResponse<RegiaoResponse> minhas(Pageable pageable) {
        return regiaoService.minhas(pageable);
    }

    @GetMapping("/descobrir")
    public PaginaResponse<RegiaoResponse> descobrir(Pageable pageable) {
        return regiaoService.descobrir(pageable);
    }

    @GetMapping("/{id}")
    public RegiaoResponse getById(@PathVariable String id) {
        return regiaoService.getById(id);
    }

    @GetMapping("/{id}/aventuras")
    public PaginaResponse<AventuraResponse> aventuras(@PathVariable String id, Pageable pageable) {
        return regiaoService.aventuras(id, pageable);
    }

    @PutMapping("/{id}")
    public RegiaoResponse atualizar(@PathVariable String id, @RequestBody @Valid RegiaoRequest request) {
        return regiaoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        regiaoService.deletar(id);
    }
}
