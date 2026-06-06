package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.CaminhoRequest;
import com.trisha.bff.model.dto.response.CaminhoResponse;
import com.trisha.bff.service.CaminhoBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bff/caminhos")
@RequiredArgsConstructor
public class CaminhoController {

    private final CaminhoBffService caminhoService;

    @PostMapping
    public CaminhoResponse iniciar(@RequestBody @Valid CaminhoRequest request) {
        return caminhoService.iniciar(request);
    }

    @PatchMapping("/{id}/finalizar")
    public CaminhoResponse finalizar(@PathVariable String id, @RequestParam Double distanciaTotalKm) {
        return caminhoService.finalizar(id, distanciaTotalKm);
    }

    @GetMapping("/aventura/{aventuraId}")
    public List<CaminhoResponse> getByAventura(@PathVariable String aventuraId) {
        return caminhoService.getByAventura(aventuraId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<CaminhoResponse> getByUsuario(@PathVariable String usuarioId) {
        return caminhoService.getByUsuario(usuarioId);
    }
}
