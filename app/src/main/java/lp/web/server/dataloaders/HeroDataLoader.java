package lp.web.server.dataloaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lp.web.server.entities.HeroEntity;
import lp.web.server.model.HeroModel;
import lp.web.server.services.HeroService;
import org.dataloader.BatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class HeroDataLoader implements BatchLoader<Long, HeroEntity> {
    private final HeroService heroService;

    @Autowired
    public HeroDataLoader(HeroService heroService) {
        this.heroService = heroService;
    }

    @Override
    public CompletionStage<List<HeroEntity>> load(List<Long> keys) {
        return CompletableFuture.supplyAsync(() -> this.heroService.getMany(keys));
    }
}
