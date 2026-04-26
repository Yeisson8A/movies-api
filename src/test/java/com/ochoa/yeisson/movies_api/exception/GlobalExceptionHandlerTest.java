package com.ochoa.yeisson.movies_api.exception;

import com.ochoa.yeisson.movies_api.enums.ErrorType;
import graphql.GraphQLError;
import graphql.execution.ExecutionStepInfo;
import graphql.schema.DataFetchingEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler handler;
    private DataFetchingEnvironment env;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();

        ExecutionStepInfo stepInfo = mock(ExecutionStepInfo.class);
        when(stepInfo.getPath()).thenReturn(mock(graphql.execution.ResultPath.class));

        env = mock(DataFetchingEnvironment.class);
        when(env.getExecutionStepInfo()).thenReturn(stepInfo);
    }

    // ========================= // CUSTOM EXCEPTION // =========================
    @Test
    void shouldReturnCustomError() {
        CustomException ex = new CustomException( "Custom error message", ErrorType.BAD_REQUEST );
        GraphQLError error = handler.resolveToSingleError(ex, env);

        assertNotNull(error);
        assertEquals("Custom error message", error.getMessage());
        assertEquals("BAD_REQUEST", error.getExtensions().get("errorType"));
    }

    // ========================= // GENERIC EXCEPTION // =========================
    @Test
    void shouldReturnInternalError() {
        RuntimeException ex = new RuntimeException("Some error");
        GraphQLError error = handler.resolveToSingleError(ex, env);

        assertNotNull(error);
        assertEquals("Internal server error", error.getMessage());
        assertEquals("INTERNAL_ERROR", error.getExtensions().get("errorType"));
    }

    // ========================= // PATH VALIDATION // =========================
    @Test
    void shouldIncludePath() {
        RuntimeException ex = new RuntimeException();

        GraphQLError error = handler.resolveToSingleError(ex, env);

        assertNotNull(error.getPath());
    }
}
