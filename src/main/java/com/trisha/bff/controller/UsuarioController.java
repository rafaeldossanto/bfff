package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.LoginSocialRequest;
import com.trisha.bff.model.dto.request.UsuarioCreateRequest;
import com.trisha.bff.model.dto.request.UsuarioUpdateRequest;
import com.trisha.bff.model.dto.response.UsuarioPublicoResponse;
import com.trisha.bff.model.dto.response.UsuarioResponse;
import com.trisha.bff.service.UsuarioBffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioBffService usuarioService;

    @PostMapping
    public UsuarioResponse create(@RequestBody @Valid UsuarioCreateRequest request) {
        return usuarioService.create(request);
    }

    @PostMapping("/login-social")
    public UsuarioResponse loginSocial(@RequestBody @Valid LoginSocialRequest request) {
        return usuarioService.loginSocial(request);
    }

    @PutMapping("/{id}")
    public UsuarioResponse update(@PathVariable String id, @RequestBody @Valid UsuarioUpdateRequest request) {
        return usuarioService.update(id, request);
    }

    @GetMapping("/{id}")
    public UsuarioResponse getById(@PathVariable String id) {
        return usuarioService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        usuarioService.delete(id);
    }

    @P