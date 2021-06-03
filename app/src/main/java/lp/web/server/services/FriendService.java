package lp.web.server.services;

import lp.web.server.entities.FriendEntity;

import java.util.List;

public interface FriendService {
    List<FriendEntity> getFriendsFor(Long id);
}
