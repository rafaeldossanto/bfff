package com.trisha.bff.controller;

import com.trisha.bff.model.dto.response.RegiaoResponse;
import com.trisha.bff.service.RegiaoBffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bff/regioes")
@RequiredArgsConstructor
public class RegiaoController {

    private final RegiaoBffService regiaoService;

    @GetMapping
    public List<RegiaoResponse> listar() {
        return regiaoService.listar();
    }
}
