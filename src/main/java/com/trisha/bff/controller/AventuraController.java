package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.AventuraRequest;
import com.trisha.bff.model.dto.response.AventuraDetalheResponse;
import com.trisha.bff.model.dto.response.AventuraResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.service.AventuraBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/aventuras")
@RequiredArgsConstructor
public class AventuraController {

    private final AventuraBffService aventuraService;

    @PostMapping
    public AventuraResponse create(@RequestBody @Valid AventuraRequest request) {
        return aventuraService.create(request);
    }

    @GetMapping("/{id}")
    public AventuraResponse getById(@PathVariable String id) {
        return aventuraService.getById(id);
    }

    @GetMapping("/{id}/detalhe")
    public AventuraDetalheResponse getDetalhe(@PathVariable String id) {
        return aventuraService.getDetalhe(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public PaginaResponse<AventuraResponse> getByUsuario(@PathVariable String usuarioId, Pageable pageable) {
        return aventuraService.getByUsuario(usuarioId, pageable);
    }

    @PatchMapping("/{id}/status")
    public AventuraResponse atualizarStatus(@PathVariable String id, @RequestParam String status) {
        return aventuraService.atualizarStatus(