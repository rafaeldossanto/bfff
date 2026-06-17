package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.SeguirRequest;
import com.trisha.bff.model.dto.response.ContadoresResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.StatusSeguirResponse;
import com.trisha.bff.model.dto.response.UsuarioPublicoResponse;
import com.trisha.bff.service.SeguidorBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/seguidores")
@RequiredArgsConstructor
public class SeguidorController {

    private final SeguidorBffService seguidorService;

    @PostMapping
    public void seguir(@RequestBody @Valid SeguirRequest request) {
        seguidorService.seguir(request.seguidoCodigo());
    }

    @DeleteMapping
    public void deixarDeSeguir(@RequestBody @Valid SeguirRequest request) {
        seguidorService.deixarDeSeguir(request.seguidoCodigo());
    }

    @GetMapping("/seguidores")
    public PaginaResponse<UsuarioPublicoResponse> seguidores(@RequestParam String codigo, Pageable pageable) {
        return seguidorService.seguidores(codigo, pageable);
    }

    @GetMapping("/seguindo")
    public PaginaResponse<UsuarioPublicoResponse> seguindo(@RequestParam String codigo, Pageable pageable) {
        return seguidorService.seguindo(codigo, pageable);
    }

    @GetMapping("/contadores")
    public ContadoresResponse contadores(@RequestParam String codigo) {
        return seguidorService.contadores(codigo);
    }

    @GetMapping("/status")
    public StatusSeguirResponse status(@RequestParam String codigo) {
        return seguidorService.status(codigo);
    }
}
