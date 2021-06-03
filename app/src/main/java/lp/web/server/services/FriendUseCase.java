package lp.web.server.services;

import com.google.inject.Inject;
import lp.web.server.entities.FriendEntity;
import lp.web.server.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendUseCase implements FriendService {
    private final FriendRepository friendRepository;

    @Autowired
    public FriendUseCase(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public List<FriendEntity> getFriendsFor(Long id) {
        return this.friendRepository.getAllFriendsById(id);
    }
}
