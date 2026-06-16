package com.trisha.bff.controller;

import com.trisha.bff.model.dto.response.ContadoresResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.model.dto.response.StatusSeguirResponse;
import com.trisha.bff.model.dto.response.UsuarioPublicoResponse;
import com.trisha.bff.service.SeguidorBffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/seguidores")
@RequiredArgsConstructor
public class SeguidorController {

    private final SeguidorBffService seguidorService;

    @PostMapping("/{seguidoId}")
    public void seguir(@PathVariable String seguidoId) {
        seguidorService.seguir(seguidoId);
    }

    @DeleteMapping("/{seguidoId}")
    public void deixarDeSeguir(@PathVariable String seguidoId) {
        seguidorService.deixarDeSeguir(seguidoId);
    }

    @GetMapping("/seguidores/{usuarioId}")
    public PaginaResponse<UsuarioPublicoResponse> seguidores(@PathVariable String usuarioId, Pageable pageable) {
        return seguidorService.seguidores(usuarioId, pageable);
    }

    @GetMapping("/seguindo/{usuarioId}")
    public PaginaResponse<UsuarioPublicoResponse> seguindo(@PathVariable String usuarioId, Pageable pageable) {
        return seguidorService.seguindo(usuarioId, pageable);
    }

    @GetMapping("/contadores/{usuarioId}")
    public ContadoresResponse contadores(@PathVariable String usuarioId) {
        return seguidorService.contadores(usuarioId);
    }

    @GetMapping("/status/{usuarioId}")
    public StatusSeguirResponse status(@PathVariable String usuarioId) {
        return seguidorService.status(usuarioId);
    }
}
