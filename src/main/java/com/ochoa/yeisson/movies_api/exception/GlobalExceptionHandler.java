package com.ochoa.yeisson.movies_api.exception;

import com.ochoa.yeisson.movies_api.enums.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex,
                                                graphql.schema.DataFetchingEnvironment env) {

        if (ex instanceof CustomException customEx) {
            return GraphqlErrorBuilder.newError()
                    .message(customEx.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .extensions(Map.of(
                            "errorType", customEx.getErrorType().name()
                    ))
                    .build();
        }

        return GraphqlErrorBuilder.newError()
                .message("Internal server error")
                .path(env.getExecutionStepInfo().getPath())
                .extensions(Map.of(
                        "errorType", ErrorType.INTERNAL_ERROR.name()
                ))
                .build();
    }
}
