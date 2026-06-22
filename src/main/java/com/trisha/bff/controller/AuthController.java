package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.DevLoginRequest;
import com.trisha.bff.model.dto.response.AuthenticationResponse;
import com.trisha.bff.service.UserBffService;
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

    private final UserBffService userService;

    @PostMapping("/dev-login")
    public AuthenticationResponse devLogin(@RequestBody @Valid DevLoginRequest request) {
        return userService.devLogin(request);
    }
}
