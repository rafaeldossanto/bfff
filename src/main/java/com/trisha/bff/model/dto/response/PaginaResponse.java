package com.trisha.bff.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Pagina generica do BFF. Os servicos downstream (APP) serializam um
 * {@link org.springframework.data.domain.Page} do Spring Data com os campos
 * {@code content}, {@code number}, {@code size}, {@code totalElements} e
 * {@code totalPages}. Desserializar diretamente em {@code Page} via RestClient
 * e fragil (PageImpl nao tem construtor padrao), entao mapeamos esse formato
 * para um record simples, com nomes em portugues, que o front consome.
 */
public record PaginaResponse<T>(
        @JsonProperty("content") List<T> conteudo,
        @JsonProperty("number") int pagina,
        @JsonProperty("size") int tamanho,
        @JsonProperty("totalElements") long total,
        @JsonProperty("totalPages") int totalPaginas
) {}
