package com.trisha.bff.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * URLs base dos microservicos downstream, lidas de application.yaml sob o
 * prefixo "servicos". Centraliza o enderecamento num unico ponto — para
 * mudar de localhost para nome de container (Docker), basta alterar o yaml.
 */
@ConfigurationProperties(prefix = "servicos")
@Getter
@Setter
public class ServicosProperties {

    private String cadastro;
    private String app;
    private String localizacao;
    private String midia;
}
