package com.trisha.bff.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

/**
 * Cria um {@link RestClient} dedicado por microservico downstream, cada um
 * com sua base URL. Um bean por servico (em vez de um RestClient generico)
 * deixa explicito quem fala com quem e permite ajustar timeouts/headers por
 * servico no futuro sem afetar os demais.
 *
 * Os beans sao nomeados (appRestClient, etc.) para serem injetados por nome
 * com @Qualifier nos clients correspondentes. Os timeouts evitam que uma
 * dependencia lenta segure uma thread do BFF indefinidamente.
 */
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
