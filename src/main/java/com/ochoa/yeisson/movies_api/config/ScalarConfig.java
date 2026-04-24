package com.ochoa.yeisson.movies_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import graphql.scalars.ExtendedScalars;

@Configuration
public class ScalarConfig {
    @Bean
    public graphql.schema.GraphQLScalarType dateTimeScalar() {
        return ExtendedScalars.DateTime;
    }
}
