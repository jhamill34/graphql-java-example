package lp.web.server.services;

import com.google.inject.Inject;
import lp.web.server.entities.HeroEntity;
import lp.web.server.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

@Service
public class HeroUseCase implements HeroService {
    private final HeroRepository heroRepository;

    @Autowired
    public HeroUseCase(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public HeroEntity get(Long id) {
        Optional<HeroEntity> entity = this.heroRepository
                .getByIds(Collections.singletonList(id))
                .stream()
                .findFirst();

        return entity.orElse(null);
    }

    @Override
    public List<HeroEntity> getMany(List<Long> ids) {
        return this.heroRepository.getByIds(ids);
    }
}
