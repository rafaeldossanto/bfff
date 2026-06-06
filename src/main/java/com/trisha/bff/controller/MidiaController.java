package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.MidiaRequest;
import com.trisha.bff.model.dto.response.MidiaResponse;
import com.trisha.bff.service.MidiaBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bff/midias")
@RequiredArgsConstructor
public class MidiaController {

    private final MidiaBffService midiaService;

    @PostMapping
    public MidiaResponse salvar(@RequestBody @Valid MidiaRequest request) {
        return midiaService.salvar(request);
    }

    @GetMapping("/aventura/{aventuraId}")
    public List<MidiaResponse> getByAventura(@PathVariable String aventuraId) {
        return midiaService.getByAventura(aventuraId);
    }

    @GetMapping("/caminho/{caminhoId}")
    public List<MidiaResponse> getByCaminho(@PathVariable String caminhoId) {
        return midiaService.getByCaminho(caminhoId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        midiaService.delete(id);
    }
}
