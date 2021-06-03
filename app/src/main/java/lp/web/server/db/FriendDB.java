package lp.web.server.db;

import com.google.inject.Singleton;
import lp.web.server.entities.FriendEntity;
import lp.web.server.repositories.FriendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FriendDB implements FriendRepository {
    private static final Logger logger = LoggerFactory.getLogger(FriendDB.class);

    private static final List<FriendEntity> FRIENDS = Stream.of(
            new FriendEntity(1L, 3L),
            new FriendEntity(1L, 2L),
            new FriendEntity(1L, 4L),

            new FriendEntity(3L, 1L),
            new FriendEntity(3L, 3L),
            new FriendEntity(3L, 4L),
            new FriendEntity(3L, 5L),

            new FriendEntity(2L, 3L),
            new FriendEntity(2L, 4L),
            new FriendEntity(2L, 1L),

            new FriendEntity(4L, 1L),
            new FriendEntity(4L, 2L),
            new FriendEntity(4L, 3L),
            new FriendEntity(4L, 5L)
    ).collect(Collectors.toList());

    @Override
    public List<FriendEntity> getAllFriendsById(Long friendId) {
        logger.info("SELECT * FROM friends WHERE id = " + friendId);
        return FRIENDS.stream()
                .filter(friend -> friendId.equals(friend.getId()))
                .collect(Collectors.toList());
    }
}
