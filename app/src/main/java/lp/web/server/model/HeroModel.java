package lp.web.server.model;

import graphql.schema.DataFetchingEnvironment;
import lp.web.server.entities.FriendEntity;
import lp.web.server.entities.HeroEntity;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class HeroModel {
    private final Long id;
    private final String name;

    public HeroModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CompletableFuture<List<HeroModel>> getFriends(DataFetchingEnvironment environment) throws Exception {
        HeroModel model = environment.getSource();
        DataLoader<Long, List<FriendEntity>> friendDataLoader = environment.getDataLoader("friends");

        // Use the friend data loader for caching
        CompletableFuture<List<FriendEntity>> futureFriendIds = friendDataLoader.load(model.getId());
        friendDataLoader.dispatch();
        List<Long> friendIds = futureFriendIds.get()
                .stream()
                .map(FriendEntity::getFriendId)
                .collect(Collectors.toList());

        DataLoader<Long, HeroEntity> heroDataLoader = environment.getDataLoader("heros");
        return heroDataLoader.loadMany(friendIds).thenApply(heros -> heros.stream()
                .map(h -> new HeroModel(h.getId(), h.getName()))
                .collect(Collectors.toList()));
    }
}
