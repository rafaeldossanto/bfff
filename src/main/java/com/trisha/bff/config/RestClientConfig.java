package com.trisha.bff.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(ServicosProperties.class)
public class RestClientConfig {

    private static final Duration TIMEOUT_CONEXAO = Duration.ofSeconds(2);
    private static final Duration TIMEOUT_LEITURA = Duration.ofSeconds(5);

    private final ServicosProperties servicos;

    public RestClientConfig(ServicosProperties servicos) {
        this.servicos = servicos;
    }

    @Bean
    public RestClient cadastroRestClient() {
        return build(servicos.getCadastro());
    }

    @Bean
    public RestClient appRestClient() {
        return build(servicos.getApp());
    }

    @Bean
    public RestClient localizacaoRestClient() {
        return build(servicos.getLocalizacao());
    }

    @Bean
    public RestClient midiaRestClient() {
        return build(servicos.getMidia());
    }

    private RestClient build(String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory())
                .build();
    }

    private SimpleClientHttpRequestFactory requestFactory() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(TIMEOUT_CONEXAO);
        factory.setReadTimeout(TIMEOUT_LEITURA);
        return factory;
    }
}
