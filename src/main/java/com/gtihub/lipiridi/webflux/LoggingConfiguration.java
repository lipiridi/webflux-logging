package com.gtihub.lipiridi.webflux;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "logging.webflux.http", name = "enabled", havingValue = "true", matchIfMissing = true)
    public LoggingWebFilter loggingWebFilter(LoggingProperties loggingProperties, Collection<HttpLogConsumer> httpLogConsumers) {
        return new LoggingWebFilter(loggingProperties.getIgnorePatterns(), httpLogConsumers);
    }
}
