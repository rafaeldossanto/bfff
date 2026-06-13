package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.DevLoginRequest;
import com.trisha.bff.model.dto.response.AutenticacaoResponse;
import com.trisha.bff.service.UsuarioBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioBffService usuarioService;

    @PostMapping("/dev-login")
    public AutenticacaoResponse devLogin(@RequestBody @Valid DevLoginRequest request) {
        return usuarioService.devLogin(request);
    }
}
