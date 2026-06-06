package com.trisha.bff.service;

import com.trisha.bff.client.CadastroClient;
import com.trisha.bff.model.dto.request.UsuarioCreateRequest;
import com.trisha.bff.model.dto.request.UsuarioUpdateRequest;
import com.trisha.bff.model.dto.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Orquestra operacoes de usuario sobre o servico Cadastro.
 *
 * Regra de cache: leitura (getById) e cacheada; escritas (update, delete)
 * invalidam a entrada correspondente com @CacheEvict, para o front nunca
 * receber um usuario desatualizado apos uma alteracao. Create nao cacheia
 * (ainda nao ha id estavel para chavear).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioBffService {

    private final CadastroClient cadastroClient;

    public UsuarioResponse create(UsuarioCreateRequest request) {
        log.info("BFF: criando usuario {}", request.email());
        return cadastroClient.create(request);
    }

    @CacheEvict(cacheNames = "usuario", key = "#id")
    public UsuarioResponse update(String id, UsuarioUpdateRequest request) {
        log.info("BFF: atualizando usuario {}", id);
        return cadastroClient.update(id, request);
    }

    @Cacheable(cacheNames = "usuario", key = "#id")
    public UsuarioResponse getById(String id) {
        log.info("BFF: buscando usuario {}", id);
        return cadastroClient.getById(id);
    }

    @CacheEvict(cacheNames = "usuario", key = "#id")
    public void delete(String id) {
        log.info("BFF: deletando usuario {}", id);
        cadastroClient.delete(id);
    }

    public String confirmarEmail(String token) {
        return cadastroClient.confirmarEmail(token);
    }

    public String aceitarTermos(String usuarioId) {
        return cadastroClient.aceitarTermos(usuarioId);
    }
}
