package lp.web.server.config;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.spring.web.servlet.ExecutionInputCustomizer;
import graphql.spring.web.servlet.GraphQLInvocation;
import graphql.spring.web.servlet.GraphQLInvocationData;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Primary
@Component
public class GraphQLInvocationWithDataLoader implements GraphQLInvocation {

    private final GraphQL graphQL;

    private final Supplier<DataLoaderRegistry> dataLoaderRegistry;

    private final ExecutionInputCustomizer executionInputCustomizer;

    @Autowired
    public GraphQLInvocationWithDataLoader(
            GraphQL graphQL,
            Supplier<DataLoaderRegistry> dataLoaderRegistry,
            ExecutionInputCustomizer executionInputCustomizer) {
        this.graphQL = graphQL;
        this.dataLoaderRegistry = dataLoaderRegistry;
        this.executionInputCustomizer = executionInputCustomizer;
    }

    @Override
    public CompletableFuture<ExecutionResult> invoke(GraphQLInvocationData invocationData, WebRequest webRequest) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(invocationData.getQuery())
                .operationName(invocationData.getOperationName())
                .variables(invocationData.getVariables())
                .dataLoaderRegistry(dataLoaderRegistry.get())
                .build();

        CompletableFuture<ExecutionInput> customizedExecutionInput = executionInputCustomizer.customizeExecutionInput(executionInput, webRequest);
        return customizedExecutionInput.thenCompose(graphQL::executeAsync);
    }
}
