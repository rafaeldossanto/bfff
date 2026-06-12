package com.trisha.bff.controller;

import com.trisha.bff.auth.UsuarioAutenticado;
import com.trisha.bff.model.dto.request.AmizadeRequest;
import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.model.dto.response.PaginaResponse;
import com.trisha.bff.service.AmizadeBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/amizades")
@RequiredArgsConstructor
public class AmizadeController {

    private final AmizadeBffService amizadeService;

    @PostMapping
    public AmizadeResponse solicitar(@RequestBody @Valid AmizadeRequest request) {
        return amizadeService.solicitar(request);
    }

    @PatchMapping("/{id}/responder")
    public AmizadeResponse responder(@PathVariable String id, @RequestParam String status) {
        return amizadeService.responder(id, status);
    }

    @GetMapping("/pendentes")
    public PaginaResponse<AmizadeResponse> getPendentes(UsuarioAutenticado usuario, Pageable pageable) {
        return amizadeService.getPendentes(usuario, pageable);
    }

    @GetMapping("/amigos")
    public PaginaResponse<AmizadeResponse> getAmigos(UsuarioAutenticado usuario, Pageable pageable) {
        return amizadeService.getAmigos(usuario, pageable);
    }
}
