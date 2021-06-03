package lp.web.server.entities;

public class FriendEntity {
    private final Long id;
    private final Long friendId;

    public FriendEntity(Long id, Long friendId) {
        this.id = id;
        this.friendId = friendId;
    }

    public Long getId() {
        return id;
    }

    public Long getFriendId() {
        return friendId;
    }
}
