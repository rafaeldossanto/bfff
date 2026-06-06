package com.trisha.bff.controller;

import com.trisha.bff.model.dto.response.AmizadeResponse;
import com.trisha.bff.service.AgregacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints do BFF relacionados a amizades. Retorna o DTO direto — o status
 * 200 e implicito e os erros (inclusive de downstream) vao pelo
 * GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/bff/amizades")
@RequiredArgsConstructor
public class AmizadeController {

    private final AgregacaoService agregacaoService;

    @GetMapping("/usuario/{usuarioId}")
    public List<AmizadeResponse> getAmigos(@PathVariable String usuarioId) {
        return agregacaoService.getAmigos(usuarioId);
    }
}
