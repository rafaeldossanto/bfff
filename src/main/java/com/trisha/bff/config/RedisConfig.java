package com.trisha.bff.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * Configura o cache Redis do BFF. As respostas agregadas sao serializadas em
 * JSON (legivel e independente de versao de classe Java) e expiram apos um TTL
 * padrao — cache de leitura de um BFF deve ser curto o suficiente para nao
 * servir dado velho, mas longo o suficiente para aliviar os downstreams.
 *
 * @EnableCaching liga o suporte a @Cacheable nos services.
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class RedisConfig {

    private final CacheProperties cacheProperties;

    public RedisConfig(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(ObjectMapper objectMapper) {
        var serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(cacheProperties.getTtlSegundos()))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(serializer));
    }
}
