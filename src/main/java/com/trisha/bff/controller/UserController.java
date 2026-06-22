package com.trisha.bff.controller;

import com.trisha.bff.model.dto.request.SocialLoginRequest;
import com.trisha.bff.model.dto.request.UserCreateRequest;
import com.trisha.bff.model.dto.request.UserUpdateRequest;
import com.trisha.bff.model.dto.response.AuthenticationResponse;
import com.trisha.bff.model.dto.response.PublicUserResponse;
import com.trisha.bff.model.dto.response.UserResponse;
import com.trisha.bff.service.UserBffService;
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
public class UserController {

    private final UserBffService userService;

    @PostMapping
    public UserResponse create(@RequestBody @Valid UserCreateRequest request) {
        return userService.create(request);
    }

    @PostMapping("/login-social")
    public AuthenticationResponse socialLogin(@RequestBody @Valid SocialLoginRequest request) {
        return userService.socialLogin(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request) {
        return userService.update(id, request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable String id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @PostMapping("/{id}/aceitar-termos")
    public String acceptTerms(@PathVariable("id") String userId) {
        return userService.acceptTerms(userId);
    }

    @GetMapping("/confirmar-email")
    public String confirmEmail(@RequestParam String token) {
        return userService.confirmEmail(token);
    }

    @GetMapping("/codigo/{codigoUsuario}")
    public PublicUserResponse findByCode(@PathVariable("codigoUsuario") String userCode) {
        return userService.findByCode(userCode);
    }

    @GetMapping("/busca")
    public List<PublicUserResponse> autocomplete(@RequestParam("termo") String term) {
        return userService.autocomplete(term);
    }
}
