package com.trisha.bff.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Tratamento de erros centralizado do BFF. Como o BFF e a porta de entrada do
 * front, ele precisa traduzir as falhas dos downstreams de forma util:
 *
 * - Erro HTTP vindo de um microservico (4xx/5xx): repassa o MESMO status e a
 *   mensagem de origem, para o front receber "aventura nao encontrada" (404) em
 *   vez de um 500 generico do BFF.
 * - Downstream fora do ar / timeout: responde 503 (Service Unavailable), que e
 *   semanticamente o que aconteceu — o BFF esta de pe, mas a dependencia nao.
 * - IllegalArgumentException: erro de validacao/regra do proprio BFF -> 400.
 * - Qualquer outra coisa: 500.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<Map<String, Object>> handleDownstream(HttpStatusCodeException ex) {
        HttpStatusCode status = ex.getStatusCode();
        log.warn("[DOWNSTREAM] {} respondeu {}: {}", ex.getClass().getSimpleName(), status, ex.getResponseBodyAsString());
        return buildResponse(status, mensagemDoDownstream(ex));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String, Object>> handleIndisponivel(ResourceAccessException ex) {
        log.error("[DOWNSTREAM] servico indisponivel: {}", ex.getMessage());
        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, "Servico temporariamente indisponivel");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("[EXCEPTION] IllegalArgumentException: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error("[EXCEPTION] Erro inesperado: {}", ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }

    private String mensagemDoDownstream(HttpStatusCodeException ex) {
        String corpo = ex.getResponseBodyAsString();
        return corpo.isBlank() ? ex.getStatusText() : corpo;
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatusCode status, String mensagem) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("mensagem", mensagem);
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(body);
    }
}
