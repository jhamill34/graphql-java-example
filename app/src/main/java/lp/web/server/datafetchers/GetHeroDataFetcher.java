package lp.web.server.datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lp.web.server.entities.HeroEntity;
import lp.web.server.model.HeroModel;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class GetHeroDataFetcher implements DataFetcher<CompletableFuture<HeroModel>> {
    @Override
    public CompletableFuture<HeroModel> get(DataFetchingEnvironment environment) throws Exception {
        DataLoader<Long, HeroEntity> dataLoader = environment.getDataLoader("heros");
        Long heroId = Long.parseLong(environment.getArgument("id"));

        return dataLoader.load(heroId).thenApply(this::mapEntityToModel);
    }

    private HeroModel mapEntityToModel(HeroEntity entity) {
        return new HeroModel(entity.getId(), entity.getName());
    }
}
