package lp.web.server.dataloaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lp.web.server.entities.FriendEntity;
import lp.web.server.services.FriendService;
import org.dataloader.BatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Component
public class FriendsDataLoader implements BatchLoader<Long, List<FriendEntity>> {
    private final FriendService friendService;

    @Autowired
    public FriendsDataLoader(FriendService friendService) {
        this.friendService = friendService;
    }

    @Override
    public CompletionStage<List<List<FriendEntity>>> load(List<Long> keys) {
        return CompletableFuture.supplyAsync(() -> keys.stream()
                .map(this.friendService::getFriendsFor)
                .collect(Collectors.toList()));
    }
}
