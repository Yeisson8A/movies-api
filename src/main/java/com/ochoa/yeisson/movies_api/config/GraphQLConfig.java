package com.ochoa.yeisson.movies_api.config;

import graphql.execution.instrumentation.Instrumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {
    @Bean
    public Instrumentation instrumentation() {
        return new graphql.execution.instrumentation.tracing.TracingInstrumentation();
    }
}
