package lp.web.server.repositories;

import lp.web.server.entities.FriendEntity;

import java.util.List;

public interface FriendRepository {
    List<FriendEntity> getAllFriendsById(Long friendId);
}
