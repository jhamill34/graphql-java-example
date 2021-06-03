package lp.web.server.config;

import com.google.inject.*;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import lp.web.server.dataloaders.FriendsDataLoader;
import lp.web.server.dataloaders.HeroDataLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

@Configuration
public class GraphQLConfiguration extends AbstractModule {
    @Value("classpath:schema.graphqls")
    private Resource schemaResource;

    @Bean
    public String schema() {
        try (Reader reader = new InputStreamReader(schemaResource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Bean
    public GraphQL provideGraphQl(GraphQLSchema schema) {
        return GraphQL.newGraphQL(schema).build();
    }

    @Bean
    public GraphQLSchema provideGraphQlSchema(
            @Qualifier("schema") String schema,
            RuntimeWiring runtimeWiring
    ) {
        SchemaParser parser = new SchemaParser();
        SchemaGenerator generator = new SchemaGenerator();

        TypeDefinitionRegistry registry = parser.parse(schema);
        return generator.makeExecutableSchema(registry, runtimeWiring);
    }

    @Bean
    public Supplier<DataLoaderRegistry> provideDataLoaderRegistry(
            HeroDataLoader heroDataLoader,
            FriendsDataLoader friendsDataLoader) {
        return () -> {
            DataLoaderRegistry registry = new DataLoaderRegistry();
            registry.register("heros", DataLoader.newDataLoader(heroDataLoader));
            registry.register("friends", DataLoader.newDataLoader(friendsDataLoader));
            return registry;
        };
    }

    @Bean
    public RuntimeWiring provideRuntimeWiring(
            DataFetcher<?> dataFetchers
    ) {
         RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
         builder.type(
                 TypeRuntimeWiring.newTypeWiring("Query")
                    .dataFetcher("hero", dataFetchers));

        return builder.build();
    }
}
